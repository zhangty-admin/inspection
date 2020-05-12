package net.whir.hos.inspection.app.service;

import net.whir.hos.inspection.app.bean.TaskList;
import net.whir.hos.inspection.app.bean.TaskListOmission;
import net.whir.hos.inspection.app.config.MyQuartzScheduler;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/5/6 11:09 上午
 */
public interface TaskListOmissionService {

    /**
     * 新增统一巡检消息
     * @param taskList
     * @param quartzScheduler
     */
    void addRemindUnified(TaskListOmission taskList, MyQuartzScheduler quartzScheduler);

    /**
     * 查询全部状态为1的统一提醒
     * @return
     */
    List<TaskListOmission> findByStatus();

    /**
     * 修改状态
     * @param taskList
     */
    void update(TaskListOmission taskList);
}
