package net.whir.hos.inspection.app.service.impl;

import net.whir.hos.inspection.app.bean.TaskList;
import net.whir.hos.inspection.app.service.MyQuartzScheduler;
import net.whir.hos.inspection.app.dao.TaskListDao;
import net.whir.hos.inspection.app.service.TaskListService;
import net.whir.hos.inspection.pc.bean.Inspection;
import net.whir.hos.inspection.pc.bean.RemindUnifiedInspectionIds;
import net.whir.hos.inspection.pc.service.InspectionService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/5/6 11:09 上午
 */

@Service
@Transactional
public class TaskListServiceImpl implements TaskListService {

    @Autowired
    private TaskListDao taskListDao;
    @Autowired
    private MyQuartzScheduler myQuartzScheduler;
    @Autowired
    private TaskListService taskListService;
    @Autowired
    private InspectionService inspectionService;

    /**
     * 保存执行统一巡检消息
     *
     * @param taskList
     * @param quartzScheduler
     */
    @Override
    public void addRemindUnified(TaskList taskList, MyQuartzScheduler quartzScheduler) {
        //执行
        try {
            quartzScheduler.addJob(taskList);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询全部状态为1的统一提醒
     *
     * @return
     */
    @Override
    public List<TaskList> findByStatus() {
        Example example = new Example(TaskList.class);
        example.createCriteria().andEqualTo("status", 1);
        return taskListDao.selectByExample(example);
    }

    /**
     * 修改状态
     *
     * @param taskList
     */
    @Override
    public void update(TaskList taskList) {
        taskListDao.updateByPrimaryKeySelective(taskList);
    }

    /**
     * 新增统一任务到数据库
     * 新增统一巡检消息定时任务
     *
     * @param remindUnifiedInspectionIds
     */
    @Override
    public void addTaskList(RemindUnifiedInspectionIds remindUnifiedInspectionIds) {
        List<Inspection> inspections = inspectionService.findById(remindUnifiedInspectionIds.getInspectionIds());
        for (Inspection inspection : inspections) {
            //应查天数
            Integer heaven = inspection.getHeaven();
            String[] split = remindUnifiedInspectionIds.getUnifiedRemind().getRemindTime().split(":");
            String time = split[0];
            String minute = split[1];
            //创建时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(new Date());

            //获取任务信息
            TaskList taskList = getTask(remindUnifiedInspectionIds, inspection, heaven, time, minute, format);
            //保存数据库
            taskListDao.insertSelective(taskList);
            //新增统一巡检消息定时任务
            taskListService.addRemindUnified(taskList, myQuartzScheduler);
        }
    }

    /**
     * 删除统一提醒任务
     * 删除正在定时的任务
     *
     * @param remindUnifiedId
     */
    @Override
    public void deleteTaskList(Long remindUnifiedId) {
        //删除统一提醒任务
        Example example = new Example(TaskList.class);
        example.createCriteria().andEqualTo("unifiedId", remindUnifiedId);
        taskListDao.deleteByExample(example);
        //删除正在定时的任务
        List<TaskList> taskLists = taskListDao.selectByExample(example);
        for (TaskList taskList : taskLists) {
            try {
                myQuartzScheduler.deleteJob(taskList.getTaskName(), taskList.getClazz());
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取任务信息
     *
     * @param remindUnifiedInspectionIds
     * @param inspection
     * @param heaven
     * @param time
     * @param minute
     * @param format
     * @return
     */
    private TaskList getTask(RemindUnifiedInspectionIds remindUnifiedInspectionIds, Inspection inspection, Integer heaven, String time, String minute, String format) {
        String cron;
        TaskList taskList = new TaskList();
        switch (heaven) {
            case 5:
                cron = "0 " + minute + " " + time + " ? * MON-FRI";
                taskList = TaskList.builder().taskName(inspection.getName() + remindUnifiedInspectionIds.getUnifiedRemind().getId())
                        .unifiedId(remindUnifiedInspectionIds.getUnifiedRemind().getId()).createTime(format).status(1)
                        .clazz(inspection.getDepartmentId().toString() + remindUnifiedInspectionIds.getUnifiedRemind().getId()).cron(cron).build();
                break;
            case 7:
                cron = "0 " + minute + " " + time + " 1/1 * ? ";
                taskList = TaskList.builder().taskName(inspection.getName() + remindUnifiedInspectionIds.getUnifiedRemind().getId())
                        .unifiedId(remindUnifiedInspectionIds.getUnifiedRemind().getId()).createTime(format).status(1)
                        .clazz(inspection.getDepartmentId().toString() + remindUnifiedInspectionIds.getUnifiedRemind().getId()).cron(cron).build();
                break;
            case 1:
                //每月底最近的工作日
                cron = "0 " + minute + " " + time + " 30W * ? ";
                taskList = TaskList.builder().taskName(inspection.getName() + remindUnifiedInspectionIds.getUnifiedRemind().getId())
                        .unifiedId(remindUnifiedInspectionIds.getUnifiedRemind().getId()).createTime(format).status(1)
                        .clazz(inspection.getDepartmentId().toString() + remindUnifiedInspectionIds.getUnifiedRemind().getId()).cron(cron).build();
                break;
        }
        return taskList;
    }


}
