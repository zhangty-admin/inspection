package net.whir.hos.inspection;

import net.whir.hos.inspection.app.config.MyJobFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: zty
 * @Date: 2020/4/7 10:14 上午
 */


@SpringBootApplication
@MapperScan(basePackages = {"net.whir.hos.inspection.*.dao"})
public class InspectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(InspectionApplication.class, args);
    }

    /**
     * 初始注入scheduler
     * @return
     * @throws SchedulerException
     */
    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) throws SchedulerException {
        return schedulerFactoryBean.getScheduler();
    }


    @Bean
    public MyJobFactory jobFactory(){
        return new MyJobFactory();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(MyJobFactory jobFactory){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory);
        return schedulerFactoryBean;
    }

}
