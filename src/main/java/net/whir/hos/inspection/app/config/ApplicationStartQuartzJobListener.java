package net.whir.hos.inspection.app.config;

import net.whir.hos.inspection.app.bean.TaskList;
import net.whir.hos.inspection.app.bean.TaskListOmission;
import net.whir.hos.inspection.app.service.MyQuartzScheduler;
import net.whir.hos.inspection.app.service.TaskListOmissionService;
import net.whir.hos.inspection.app.service.TaskListService;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

@Configuration
public class ApplicationStartQuartzJobListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private MyQuartzScheduler quartzScheduler;
    @Autowired
    private TaskListService unifiedNotificationService;
    @Autowired
    private TaskListOmissionService omissionService;

    /**
     * 初始启动quartz
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            //查询出所有要提醒的定时任务
            List<TaskList> taskList = unifiedNotificationService.findByStatus();
            List<TaskListOmission> taskListOmissions = omissionService.findByStatus();

            //启动所有的要提醒的定时任务
            quartzScheduler.startJob(taskList);
            quartzScheduler.startJobOmission(taskListOmissions);
            System.out.println("任务已经启动...");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始注入scheduler
     *
     * @return
     * @throws SchedulerException
     */
    @Bean
    public Scheduler scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
        return schedulerFactoryBean.getScheduler();
    }

}