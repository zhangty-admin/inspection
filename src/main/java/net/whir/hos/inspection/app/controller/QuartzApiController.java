package net.whir.hos.inspection.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.whir.hos.inspection.app.bean.TaskList;
import net.whir.hos.inspection.app.config.MyQuartzScheduler;
import net.whir.hos.inspection.app.service.TaskListService;
import net.whir.hos.inspection.commons.entity.Result;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *
 *
 * @author yvan
 */
@RestController
@CrossOrigin
@RequestMapping("/quartz")
@Api(description = "消息通知")
public class QuartzApiController {

    @Autowired
    private MyQuartzScheduler quartzScheduler;
    @Autowired
    private TaskListService unifiedNotificationService;
    @Autowired
    private TaskListService taskListService;

    @ApiOperation("启动全部任务")
    @GetMapping("/start")
    public void startQuartzJob() {
        try {
            List<TaskList> taskList = unifiedNotificationService.findByStatus();
            quartzScheduler.startJob(taskList);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("获取全部任务")
    @GetMapping("/getAll")
    public List<String> getAllQuartzJob() {
        List<String> allJobInfo = null;
        try {
            allJobInfo = quartzScheduler.getAllJobInfo();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return allJobInfo;
    }


    @ApiOperation(value = "新增巡检消息提醒")
    @PostMapping("/add")
    public Result addQuartzJob(@RequestBody TaskList taskList) {
        unifiedNotificationService.addRemindUnified(taskList, quartzScheduler);
        return new Result();
    }

    @ApiOperation("获取单个任务")
    @PostMapping("/info")
    public String getQuartzJob(@RequestBody TaskList taskList) {
        String info = null;
        try {
            info = quartzScheduler.getJobInfo(taskList);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return info;
    }

    @ApiOperation("修改单个任务")
    @PutMapping("/modify")
    public boolean modifyQuartzJob(@RequestBody TaskList taskList) {
        boolean flag = true;
        try {
            flag = quartzScheduler.modifyJob(taskList);
            taskListService.update(taskList);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @ApiOperation("暂停单个任务")
    @PostMapping(value = "/pause")
    public void pauseQuartzJob(String name, String group) {
        try {
            quartzScheduler.pauseJob(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("暂停全部任务")
    @PostMapping(value = "/pauseAll")
    public void pauseAllQuartzJob() {
        try {
            quartzScheduler.pauseAllJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("删除单个任务")
    @DeleteMapping(value = "/delete")
    public void deleteJob(String name, String group) {
        try {
            quartzScheduler.deleteJob(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
