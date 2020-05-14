package net.whir.hos.inspection.pc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.commons.utils.MultiRequest;
import net.whir.hos.inspection.pc.bean.*;
import net.whir.hos.inspection.pc.dao.EmployeeDao;
import net.whir.hos.inspection.pc.dao.FileDao;
import net.whir.hos.inspection.pc.dao.SpecialEventDao;
import net.whir.hos.inspection.pc.service.EmployeeService;
import net.whir.hos.inspection.pc.service.FileService;
import net.whir.hos.inspection.pc.service.SpecialEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/26 2:53 下午
 */

@Service
@Transactional
public class SpecialEventServiceImpl implements SpecialEventService {

    @Autowired
    private SpecialEventDao specialEventDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private EmployeeServiceImpl employeeService;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private FileService fileService;

    /**
     * 添加特殊事件
     *
     * @param specialEventFile
     */
    @Override
    public void insertSpecialEvent(SpecialEventFile specialEventFile) {
        //添加特殊事件
        SpecialEvent specialEvent = specialEventFile.getSpecialEvent();
        specialEvent.setIsCheck(false);
        specialEventDao.insert(specialEvent);
        //图片上传 beat64保存数据库
        for (Files files : specialEventFile.getFile()) {
            files.setFiles(files.getFiles());
            files.setTitle(files.getTitle());
            files.setSpecialId(specialEventFile.getSpecialEvent().getId());
            fileService.insert(files);
        }
        //消息提醒
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        List<Employee> employees = employeeDao.selectByInspectionId(specialEvent.getInspectionId());
        StringBuilder stringBuilder = new StringBuilder();
        for (Employee employee : employees) {
            stringBuilder.append("\",\n" +
                    "  \"msgtype\": \"markdown\",\n" +
                    "  \"agentid\": 1000002,\n" +
                    "  \"markdown\": {\n" +
                    "    \"content\": \"安全巡检系统\n" +
                    "    特殊事件提醒\n" +
                    "    特殊事件，请及时查看\n" +
                    "    检查场所: " + employee.getDepartment().getName() + "\n" +
                    "    计划巡检日期: " + format + "\n" +
                    "    \"");
            StringBuilder sb = employeeService.splicingJson(employee, stringBuilder.toString());
            employeeService.sendMessage(sb.toString());
        }
    }

    @Override
    public SpecialEvent selectById(Long id) {
        return specialEventDao.selectByPrimaryKey(id);
    }

    /**
     * 删除特殊事件
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<Long> ids) {
        for (Long id : ids) {
            specialEventDao.deleteByPrimaryKey(id);
            Example example = new Example(Files.class);
            example.createCriteria().andEqualTo("specialId", id);
            fileDao.deleteByExample(example);
        }
    }

    /**
     * 分页查看特殊事件
     *
     * @param pageRequest
     * @return
     */
    @Override
    public Page<SpecialEvent> findPage(PageRequest<SpecialEvent> pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<SpecialEvent> specialEvents = specialEventDao.selectPage(pageRequest.getObj());
        return (Page<SpecialEvent>) specialEvents;
    }

    /**
     * 根据ID查询特殊事件
     *
     * @param id
     * @return
     */
    @Override
    public SpecialEvent findSpecialEventById(Long id) {
        SpecialEvent specialEvent = specialEventDao.selectByPrimaryKey(id);
        if (!StringUtils.isEmpty(specialEvent.getIsCheck()) && specialEvent.getIsCheck()) {
            //发消息
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(new Date());
            Employee employee = employeeDao.selectByPrimaryKey(specialEvent.getEmployeeId());

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\",\n" +
                    "  \"msgtype\": \"markdown\",\n" +
                    "  \"agentid\": 1000002,\n" +
                    "  \"markdown\": {\n" +
                    "    \"content\": \"安全巡检系统\n" +
                    "    特殊事件\n" +
                    "    已查阅\n" +
                    "    检查场所: " + employee.getDepartment().getName() + "\n" +
                    "    计划巡检日期: " + format + "\n" +
                    "    \"");

            StringBuilder sb = employeeService.splicingJson(employee, stringBuilder.toString());
            employeeService.sendMessage(sb.toString());
            //发送完成修改状态
            specialEvent.setIsCheck(true);
            specialEventDao.updateByPrimaryKeySelective(specialEvent);
        }
        Example example = new Example(Files.class);
        example.createCriteria().andEqualTo("specialId", id);
        List<Files> files = fileDao.selectByExample(example);
        specialEvent.setFiles(files);
        return specialEvent;
    }


}
