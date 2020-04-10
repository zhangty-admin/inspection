package net.whir.hos.inspection.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务

/**
 * @Author: zty
 * @Date: 2020/4/7 10:14 上午
 */
public class TaskService {

    /*@Autowired
    private MysqlDao mysqlDao;*/

    /*@Scheduled(fixedRate = 5000)
    public void deleteInvalidCheckCode() {
        System.out.println("定时成功");
    }*/
}