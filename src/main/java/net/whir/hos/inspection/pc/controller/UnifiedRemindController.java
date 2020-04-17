package net.whir.hos.inspection.pc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.pc.bean.UnifiedRemind;
import net.whir.hos.inspection.pc.bean.UnifiedRemindDepartmentIds;
import net.whir.hos.inspection.pc.service.UnifiedRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/14 1:42 下午
 */

@RestController
@Slf4j
@CrossOrigin
@Api(description = "提醒时间")
@RequestMapping("/unifiedRemind")
public class UnifiedRemindController {

    @Autowired
    private UnifiedRemindService remindService;

    @ApiOperation(value = "分页查询统一提醒")
    @PostMapping("/findPage")
    private Result findPageRemind(@RequestBody PageRequest<UnifiedRemind> remindPageRequest) {
        List<UnifiedRemind> unifiedRemindList = remindService.findPage(remindPageRequest);
        return new Result(true, StatusCode.OK, "查询成功", unifiedRemindList);
    }

    @ApiOperation(value = "新增统一提醒")
    @PostMapping("/add")
    private Result addRemind(@RequestBody UnifiedRemindDepartmentIds unifiedRemindDepartment) {
        try {
            remindService.add(unifiedRemindDepartment);
        } catch (Exception e) {
            log.warn("添加失败: " + e.getMessage());
            return new Result(true, StatusCode.ERROR, "添加失败");
        }
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @ApiOperation(value = "删除统一新增提醒")
    @DeleteMapping("/delete")
    private Result deleteRemind(@RequestBody UnifiedRemindDepartmentIds unifiedRemindDepartment) {
        try {
            remindService.deleteRemindById(unifiedRemindDepartment);
        } catch (Exception e) {
            log.warn("删除失败: " + e.getMessage());
            return new Result(true, StatusCode.ERROR, "删除失败");
        }
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @ApiOperation(value = "修改统一新增提醒")
    @PutMapping("/update")
    private Result updateRemind(@RequestBody UnifiedRemindDepartmentIds unifiedRemindDepartment) {
        try {
            remindService.updateRemind(unifiedRemindDepartment);
        } catch (Exception e) {
            log.warn("修改失败: " + e.getMessage());
            return new Result(true, StatusCode.ERROR, "修改失败");
        }
        return new Result(true, StatusCode.OK, "修改成功");
    }

}
