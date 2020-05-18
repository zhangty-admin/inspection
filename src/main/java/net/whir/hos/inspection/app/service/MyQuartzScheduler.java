package net.whir.hos.inspection.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import net.whir.hos.inspection.app.bean.TaskList;
import net.whir.hos.inspection.app.bean.TaskListOmission;
import net.whir.hos.inspection.app.job.SchedulerQuartzJob1;
import net.whir.hos.inspection.app.job.SchedulerQuartzJob2;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 任务调度处理
 *
 * @Author: zty
 * @Date: 2020/5/7 1:52 下午
 */
@Configuration
public class MyQuartzScheduler {
    // 任务调度
    @Autowired
    private Scheduler scheduler;

    /**
     * 新增执行任务
     *
     * @throws SchedulerException
     */
    public void addJob(TaskList taskList) throws SchedulerException {
        startJob1(scheduler, taskList);
        scheduler.start();
    }

    /**
     * 新增执行漏检
     * @param taskListOmission
     * @throws SchedulerException
     */
    public void addJob(TaskListOmission taskListOmission) throws SchedulerException {
        startJob1(scheduler, taskListOmission);
        scheduler.start();
    }
    /**
     * 新增执行任务
     *
     * @throws SchedulerException
     */
    public void addOmissionJob(TaskListOmission taskList) throws SchedulerException {
        startJob1(scheduler, taskList);
        scheduler.start();
    }

    /**
     * 开始执行统一消息所有任务
     *
     * @throws SchedulerException
     */
    public void startJob(List<TaskList> taskList) throws SchedulerException {
        for (TaskList list : taskList) {
            startJob1(scheduler, list);
        }
        scheduler.start();
    }

    /**
     * 开始执行漏检消息所有任务
     *
     * @throws SchedulerException
     */
    public void startJobOmission(List<TaskListOmission> taskList) throws SchedulerException {
        for (TaskListOmission list : taskList) {
            startJob1(scheduler, list);
        }
        scheduler.start();
    }

    /**
     * 获取Job信息
     *
     * @param taskList
     * @return
     * @throws SchedulerException
     */
    public String getJobInfo(TaskList taskList) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(taskList.getTaskName(), taskList.getClazz());
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        return String.format("time:%s,state:%s", cronTrigger.getCronExpression(),
                scheduler.getTriggerState(triggerKey).name());
    }

    /**
     * 获取所有Job信息
     *
     * @return
     * @throws SchedulerException
     */
    public List<String> getAllJobInfo() throws SchedulerException {
        List<String> jobs = new ArrayList<>();
        Set<JobKey> jobKeySet = scheduler.getJobKeys(GroupMatcher.anyGroup());
        for (JobKey jobKey : jobKeySet) {
            String jobInfo = getJobInfo(TaskList.builder().clazz(jobKey.getGroup()).taskName(jobKey.getName()).build());
            jobs.add(jobInfo);
        }
        return jobs;
    }

    /**
     * 修改某个任务的执行时间
     *
     * @param taskList
     * @return
     * @throws SchedulerException
     */
    public boolean modifyJob(TaskList taskList) throws SchedulerException {
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(taskList.getTaskName(), taskList.getClazz());
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        String oldTime = cronTrigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(taskList.getCron())) {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(taskList.getCron());
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(taskList.getTaskName(), taskList.getClazz())
                    .withSchedule(cronScheduleBuilder).usingJobData("id", taskList.getUnifiedId()).build();
            date = scheduler.rescheduleJob(triggerKey, trigger);
        }
        return date != null;
    }

    /**
     * 暂停所有任务
     *
     * @throws SchedulerException
     */
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 暂停某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void pauseJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复所有任务
     *
     * @throws SchedulerException
     */
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 恢复某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void resumeJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void deleteJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.deleteJob(jobKey);
    }

    private void startJob1(Scheduler scheduler, TaskList taskList) throws SchedulerException {
        // 通过JobBuilder构建JobDetail实例，JobDetail规定只能是实现Job接口的实例
        // JobDetail 是具体Job实例
        JobDetail jobDetail = JobBuilder.newJob(SchedulerQuartzJob1.class).withIdentity(taskList.getTaskName(), taskList.getClazz()).build();
        //        // 基于表达式构建触发器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(taskList.getCron());
        // CronTrigger表达式触发器 继承于Trigger
        // TriggerBuilder 用于构建触发器实例
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(taskList.getTaskName(), taskList.getClazz())
                .withSchedule(cronScheduleBuilder).usingJobData("id", taskList.getUnifiedId()).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }


    private void startJob1(Scheduler scheduler, TaskListOmission taskList) throws SchedulerException {
        // 通过JobBuilder构建JobDetail实例，JobDetail规定只能是实现Job接口的实例
        // JobDetail 是具体Job实例
        JobDetail jobDetail = JobBuilder.newJob(SchedulerQuartzJob2.class).withIdentity(taskList.getTaskName(), taskList.getClazz()).build();
        //        // 基于表达式构建触发器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(taskList.getCron());
        // CronTrigger表达式触发器 继承于Trigger
        // TriggerBuilder 用于构建触发器实例
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(taskList.getTaskName(), taskList.getClazz())
                .withSchedule(cronScheduleBuilder).usingJobData("id", taskList.getMissedId()).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }
}
