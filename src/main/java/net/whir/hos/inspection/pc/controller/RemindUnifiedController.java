package net.whir.hos.inspection.pc.controller;

import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.app.service.MyQuartzScheduler;
import net.whir.hos.inspection.app.service.TaskListService;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.commons.entity.PageResult;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.pc.bean.RemindUnified;
import net.whir.hos.inspection.pc.bean.RemindUnifiedInspectionIds;
import net.whir.hos.inspection.pc.service.RemindUnifiedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zty
 * @Date: 2020/4/14 1:42 下午
 */

@RestController
@Slf4j
@CrossOrigin
@Api(description = "统一消息")
@RequestMapping("/unifiedRemind")
public class RemindUnifiedController {

    @Autowired
    private RemindUnifiedService remindService;
    @Autowired
    private MyQuartzScheduler myQuartzScheduler;
    @Autowired
    private TaskListService taskListService;


    @ApiOperation(value = "分页查询统一提醒")
    @PostMapping("/findPage")
    private Result findPageRemind(@RequestBody PageRequest<RemindUnified> remindPageRequest) {
        Page<RemindUnified> page = remindService.findPage(remindPageRequest);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<RemindUnified>(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), page.getResult()));
    }

    @ApiOperation(value = "新增统一提醒")
    @PostMapping("/add")
    private Result addRemind(@RequestBody RemindUnifiedInspectionIds unifiedRemindDepartment) {
        try {
            remindService.addRemindUnified(unifiedRemindDepartment);
        } catch (Exception e) {
            log.warn("添加失败: " + e.getMessage());
            return new Result(true, StatusCode.ERROR, "添加失败");
        }
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @ApiOperation(value = "删除统一新增提醒")
    @DeleteMapping("/delete/{remindOmissionId}")
    private Result deleteRemind(@PathVariable Long remindOmissionId) {
        try {
            remindService.deleteRemindById(remindOmissionId);
        } catch (Exception e) {
            log.warn("删除失败: " + e.getMessage());
            return new Result(true, StatusCode.ERROR, "删除失败");
        }
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @ApiOperation(value = "修改统一新增提醒")
    @PutMapping("/update")
    private Result updateRemind(@RequestBody RemindUnifiedInspectionIds unifiedRemindDepartment) {
        try {
            remindService.updateRemind(unifiedRemindDepartment);
        } catch (Exception e) {

            log.warn("修改失败: " + e.getMessage());
            return new Result(true, StatusCode.ERROR, "修改失败");
        }
        return new Result(true, StatusCode.OK, "修改成功");
    }

}
