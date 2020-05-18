package net.whir.hos.inspection.app.service;

import net.whir.hos.inspection.app.bean.TaskListOmission;
import net.whir.hos.inspection.pc.bean.RemindOmissionInspectionIds;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/5/6 11:09 上午
 */
public interface TaskListOmissionService {

    /**
     * 新增统一巡检消息
     *
     * @param taskList
     * @param quartzScheduler
     */
    void addRemindUnified(TaskListOmission taskList, MyQuartzScheduler quartzScheduler);

    /**
     * 查询全部状态为1的统一提醒
     *
     * @return
     */
    List<TaskListOmission> findByStatus();

    /**
     * 修改状态
     *
     * @param taskList
     */
    void update(TaskListOmission taskList);

    /**
     * 修改定时任务(删除重新添加)
     *
     * @param id
     */
    void deleteTaskListOmission(Integer id);

    /**
     * 添加新定时漏检任务
     *
     * @param remindOmissionDepartmentIds
     */
    void addTaskListOmission(RemindOmissionInspectionIds remindOmissionDepartmentIds);

    /**
     * 新增漏检巡检消息定时任务
     *
     * @param taskListOmission
     * @param quartzScheduler
     */
    void addRemindOmissionTask(TaskListOmission taskListOmission, MyQuartzScheduler quartzScheduler);

}
