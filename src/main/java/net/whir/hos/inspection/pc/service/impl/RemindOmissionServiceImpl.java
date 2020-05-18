package net.whir.hos.inspection.pc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.whir.hos.inspection.app.service.TaskListOmissionService;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.*;
import net.whir.hos.inspection.pc.dao.InspectionDao;
import net.whir.hos.inspection.pc.dao.InspectionHistoryDao;
import net.whir.hos.inspection.pc.dao.RemindOmissionDao;
import net.whir.hos.inspection.pc.dao.RemindOmissionInspectionDao;
import net.whir.hos.inspection.pc.service.RemindOmissionService;
import net.whir.hos.inspection.commons.utils.MappingTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/17 3:33 下午
 */

@Service
@Transactional
public class RemindOmissionServiceImpl implements RemindOmissionService {

    @Autowired
    private RemindOmissionDao remindOmissionDao;
    @Autowired
    private RemindOmissionInspectionDao remindOmissionInspectionDao;
    @Autowired
    private TaskListOmissionService taskListOmissionService;
    @Autowired
    private InspectionHistoryDao inspectionHistoryDao;
    @Autowired
    private InspectionDao inspectionDao;

    /**
     * 分页查询漏检提醒
     *
     * @param remindPageRequest
     * @return
     */
    @Override
    public Page<RemindOmission> findPage(PageRequest<RemindOmission> remindPageRequest) {
        PageHelper.startPage(remindPageRequest.getPageNum(), remindPageRequest.getPageSize());
        return (Page<RemindOmission>) remindOmissionDao.findPageRemindOmission(remindPageRequest.getObj());
    }

    /**
     * 新增漏检提醒
     *
     * @param remindOmissionInspectionIds
     */
    @Override
    public void addRemind(RemindOmissionInspectionIds remindOmissionInspectionIds) {
        //添加漏检提醒
        remindOmissionDao.insert(remindOmissionInspectionIds.getRemindOmission());
        //添加中间表信息
        MappingTableUtil.relateMapping(remindOmissionInspectionDao, new RemindOmissionInspection(),
                Long.valueOf(remindOmissionInspectionIds.getRemindOmission().getId()), remindOmissionInspectionIds.getInspectionIds(), "insert");
        //添加漏检任务列表到数据库
        //新增漏检巡检消息定时任务
        taskListOmissionService.addTaskListOmission(remindOmissionInspectionIds);
    }

    /**
     * 删除漏检新增提醒
     *
     * @param remindOmissionId
     */
    @Override
    public void deleteRemindById(Integer remindOmissionId) {
        //删除中间表信息
        delete(remindOmissionId);
        //删除漏检信息
        remindOmissionDao.deleteByPrimaryKey(remindOmissionId);
        //删除漏检
        //删除正在定时的任务
        taskListOmissionService.deleteTaskListOmission(remindOmissionId);
    }

    /**
     * 修改漏检新增提醒
     *
     * @param remindOmissionDepartmentIds
     */
    @Override
    public void updateRemind(RemindOmissionInspectionIds remindOmissionDepartmentIds) {
        //删除中间表信息
        delete(remindOmissionDepartmentIds.getRemindOmission().getId());
        //新增中间表信息
        MappingTableUtil.relateMapping(remindOmissionInspectionDao, new RemindOmissionInspection(),
                Long.valueOf(remindOmissionDepartmentIds.getRemindOmission().getId()), remindOmissionDepartmentIds.getInspectionIds(), "insert");
        //修改漏检信息
        remindOmissionDao.updateByPrimaryKeySelective(remindOmissionDepartmentIds.getRemindOmission());

        //修改定时任务(删除重新添加)
        taskListOmissionService.deleteTaskListOmission(remindOmissionDepartmentIds.getRemindOmission().getId());
        //添加新任务
        taskListOmissionService.addTaskListOmission(remindOmissionDepartmentIds);
    }


    /**
     * 删除中间表信息
     *
     * @param remindOmissionId
     * @return
     */
    private boolean delete(Integer remindOmissionId) {
        if (StringUtils.isEmpty(remindOmissionId)) {
            return false;
        }
        Example example = new Example(RemindOmissionInspection.class);
        example.createCriteria().andEqualTo("remindOmissionId", remindOmissionId);
        remindOmissionInspectionDao.deleteByExample(example);
        return true;
    }

    /**
     * 周期执行 8:00
     */
    @Scheduled(cron = "0 0 8 ? * MON-FRI")
    private void PeriodicExecution() {
        System.out.println("昨日漏检统计周期执行 8:00");
        //获取日期
        String date = getDate(true);
        //获取昨天漏检的信息
        List<Inspection> inspectionList = getInspectionOmissions(date, 5);
        //数据JSON传给前端
        String json = JSON.toJSONString(inspectionList);
        // TODO 发消息

    }


    /**
     * 每天执行 8:00
     */
    @Scheduled(cron = "0 0 8 ? * *")
    private void ExecuteEveryDay() {
        System.out.println("昨日漏检统计每天执行 8:00");
        //获取日期
        String date = getDate(false);
        //获取昨天漏检的信息
        List<Inspection> inspectionOmissions = getInspectionOmissions(date, 7);
        //数据JSON传给前端
        String json = JSON.toJSONString(inspectionOmissions);
        // TODO 发消息

    }

    /**
     * 每月最后的周五 8:00
     */
    @Scheduled(cron = "0 0 8 ? * 6L")
    private void LastFridayOfEveryMonth() {
        System.out.println("昨日漏检统计每月最后的周五 8:00");
        //获取日期
        String date = getDate(false);
        //获取昨天漏检的信息
        List<Inspection> inspectionOmissions = getInspectionOmissions(date, 1);
        //数据JSON传给前端
        String json = JSON.toJSONString(inspectionOmissions);
        // TODO 发消息
    }


    /**
     * 获取日期
     *
     * @param bool 是否需要检查为周一
     * @return
     */
    private String getDate(Boolean bool) {
        String date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        //判断是不是周一
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        if (bool) {
            if (week == 1) {
                //获取上周五的日期
                int day = cal.get(Calendar.DATE) - 2;
                date = year + "-" + month + "-" + day;
                return date;
            }
        }
        //获取当天的日期
        int day = cal.get(Calendar.DATE) - 1;
        date = year + "-" + month + "-" + day;
        return date;
    }


    private List<Inspection> getInspectionOmissions(String date, int heaven) {
        //获取工作日检查的巡检
        Example inspectionExample = new Example(Inspection.class);
        inspectionExample.createCriteria().andEqualTo("heaven", heaven);
        List<Inspection> inspections = inspectionDao.selectByExample(inspectionExample);

        List<Inspection> inspectionList = new ArrayList<>();
        //判断每个巡检任务有无漏检
        for (Inspection inspection : inspections) {
            Example inspectionHistoryExample = new Example(InspectionHistory.class);
            inspectionHistoryExample.createCriteria().andEqualTo("createTime", date)
                    .andEqualTo("frequency", inspection.getFrequency()).andEqualTo("inspectionId", inspection.getId());
            List<InspectionHistory> inspectionHistories = inspectionHistoryDao.selectByExample(inspectionHistoryExample);
            //不为空说明巡检计划频率对了
            if (StringUtils.isEmpty(inspectionHistories)) {
                //为空说明漏检了
                inspectionList.add(inspection);
            }
        }
        return inspectionList;
    }

}
