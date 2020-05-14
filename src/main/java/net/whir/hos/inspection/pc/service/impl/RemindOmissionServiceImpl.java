package net.whir.hos.inspection.pc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.whir.hos.inspection.app.config.MyQuartzScheduler;
import net.whir.hos.inspection.app.service.TaskListOmissionService;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.*;
import net.whir.hos.inspection.pc.dao.RemindOmissionDao;
import net.whir.hos.inspection.pc.dao.RemindOmissionInspectionDao;
import net.whir.hos.inspection.pc.service.RemindOmissionService;
import net.whir.hos.inspection.commons.utils.MappingTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

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
    private MyQuartzScheduler myQuartzScheduler;

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
}
