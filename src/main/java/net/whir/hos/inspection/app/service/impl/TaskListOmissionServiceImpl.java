package net.whir.hos.inspection.app.service.impl;

import net.whir.hos.inspection.app.bean.TaskListOmission;
import net.whir.hos.inspection.app.service.MyQuartzScheduler;
import net.whir.hos.inspection.app.dao.TaskListOmissionDao;
import net.whir.hos.inspection.app.service.TaskListOmissionService;
import net.whir.hos.inspection.pc.bean.Inspection;
import net.whir.hos.inspection.pc.bean.RemindOmissionInspectionIds;
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
public class TaskListOmissionServiceImpl implements TaskListOmissionService {

    @Autowired
    private TaskListOmissionDao taskListOmissionDao;
    @Autowired
    private MyQuartzScheduler myQuartzScheduler;
    @Autowired
    private InspectionService inspectionService;

    /**
     * 新增漏检巡检消息
     *
     * @param taskList
     * @param quartzScheduler
     */
    @Override
    public void addRemindUnified(TaskListOmission taskList, MyQuartzScheduler quartzScheduler) {
        //保存
        taskListOmissionDao.insertSelective(taskList);
        //执行
        try {
            quartzScheduler.addOmissionJob(taskList);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询全部状态为1的漏检提醒
     *
     * @return
     */
    @Override
    public List<TaskListOmission> findByStatus() {
        Example example = new Example(TaskListOmission.class);
        example.createCriteria().andEqualTo("status", 1);
        return taskListOmissionDao.selectByExample(example);
    }

    /**
     * 修改状态
     *
     * @param taskList
     */
    @Override
    public void update(TaskListOmission taskList) {
        taskListOmissionDao.updateByPrimaryKeySelective(taskList);
    }

    /**
     * 删除漏检提醒任务
     * 删除正在定时的任务
     *
     * @param remindOmissionId
     */
    @Override
    public void deleteTaskListOmission(Integer remindOmissionId) {
        //删除统一提醒任务
        Example example = new Example(TaskListOmission.class);
        example.createCriteria().andEqualTo("missedId", remindOmissionId);
        taskListOmissionDao.deleteByExample(example);
        //删除正在定时的任务
        List<TaskListOmission> taskLists = taskListOmissionDao.selectByExample(example);
        for (TaskListOmission taskList : taskLists) {
            try {
                myQuartzScheduler.deleteJob(taskList.getTaskName(), taskList.getClazz());
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加漏检任务列表到数据库
     * 新增漏检巡检消息定时任务
     *
     * @param remindOmissionInspectionIds
     */
    @Override
    public void addTaskListOmission(RemindOmissionInspectionIds remindOmissionInspectionIds) {
        List<Inspection> inspections = inspectionService.findById(remindOmissionInspectionIds.getInspectionIds());

        for (Inspection inspection : inspections) {
            //应查天数
            Integer heaven = inspection.getHeaven();
            //获取时间
            String reminderTime = remindOmissionInspectionIds.getRemindOmission().getReminderTime();
            String[] split = reminderTime.split(":");
            String time = split[0];
            String minute = split[1];
            //创建时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(new Date());

            //获取任务信息
            TaskListOmission taskListOmission = getTask(remindOmissionInspectionIds, inspection, heaven, time, minute, format);
            //保存数据库
            taskListOmissionDao.insertSelective(taskListOmission);
            //新增统一巡检消息定时任务
            addRemindOmissionTask(taskListOmission, myQuartzScheduler);
        }
    }

    /**
     * 新增漏检巡检消息定时任务
     *
     * @param taskListOmission
     * @param quartzScheduler
     */
    @Override
    public void addRemindOmissionTask(TaskListOmission taskListOmission, MyQuartzScheduler quartzScheduler) {
        //执行
        try {
            quartzScheduler.addJob(taskListOmission);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取漏检任务信息
     *
     * @param remindOmissionInspectionIds
     * @param inspection
     * @param heaven
     * @param time
     * @param minute
     * @param format
     * @return
     */
    private TaskListOmission getTask(RemindOmissionInspectionIds remindOmissionInspectionIds, Inspection inspection, Integer heaven, String time, String minute, String format) {
        String cron;
        TaskListOmission taskList = new TaskListOmission();
        switch (heaven) {
            case 5:
                cron = "0 " + minute + " " + time + " ? * MON-FRI";
                taskList = TaskListOmission.builder().taskName(inspection.getName() + remindOmissionInspectionIds.getRemindOmission().getId())
                        .missedId(remindOmissionInspectionIds.getRemindOmission().getId().longValue()).createTime(format).status(1)
                        .clazz(inspection.getDepartmentId().toString() + remindOmissionInspectionIds.getRemindOmission().getId()).cron(cron).build();
                break;
            case 7:
                cron = "0 " + minute + " " + time + " 1/1 * ? ";
                taskList = TaskListOmission.builder().taskName(inspection.getName() + remindOmissionInspectionIds.getRemindOmission().getId())
                        .missedId(remindOmissionInspectionIds.getRemindOmission().getId().longValue()).createTime(format).status(1)
                        .clazz(inspection.getDepartmentId().toString() + remindOmissionInspectionIds.getRemindOmission().getId()).cron(cron).build();
                break;
            case 1:
                //每月底最近的工作日
                cron = "0 " + minute + " " + time + " 30W * ? ";
                taskList = TaskListOmission.builder().taskName(inspection.getName() + remindOmissionInspectionIds.getRemindOmission().getId())
                        .missedId(remindOmissionInspectionIds.getRemindOmission().getId().longValue()).createTime(format).status(1)
                        .clazz(inspection.getDepartmentId().toString() + remindOmissionInspectionIds.getRemindOmission().getId()).cron(cron).build();
                break;
        }
        return taskList;
    }
}
