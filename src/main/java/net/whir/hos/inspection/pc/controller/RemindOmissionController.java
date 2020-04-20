package net.whir.hos.inspection.pc.controller;

import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.commons.entity.PageResult;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.pc.bean.RemindOmission;
import net.whir.hos.inspection.pc.bean.RemindOmissionDepartmentIds;
import net.whir.hos.inspection.pc.service.RemindOmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/17 3:01 下午
 */

@RestController
@CrossOrigin
@Slf4j
@Api(description = "漏检消息")
@RequestMapping("/omission")
public class RemindOmissionController {

    @Autowired
    private RemindOmissionService remindOmissionService;

    @ApiOperation(value = "分页查询漏检提醒")
    @PostMapping("/findPage")
    private Result findPageRemindOmission(@RequestBody PageRequest<RemindOmission> remindPageRequest) {
        Page<RemindOmission> page = remindOmissionService.findPage(remindPageRequest);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<RemindOmission>(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), page.getResult()));
    }

    @ApiOperation(value = "新增漏检提醒")
    @PostMapping("/add")
    private Result addRemind(@RequestBody RemindOmissionDepartmentIds remindOmissionDepartmentIds) {
        try {
            remindOmissionService.add(remindOmissionDepartmentIds);
        } catch (Exception e) {
            log.warn("添加失败: " + e.getMessage());
            return new Result(true, StatusCode.ERROR, "添加失败");
        }
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @ApiOperation(value = "删除漏检新增提醒")
    @DeleteMapping("/delete/{remindOmissionId}")
    private Result deleteRemind(@PathVariable Integer remindOmissionId) {
        try {
            remindOmissionService.deleteRemindById(remindOmissionId);
        } catch (Exception e) {
            log.warn("删除失败: " + e.getMessage());
            return new Result(true, StatusCode.ERROR, "删除失败");
        }
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @ApiOperation(value = "修改漏检新增提醒")
    @PutMapping("/update")
    private Result updateRemind(@RequestBody RemindOmissionDepartmentIds remindOmissionDepartmentIds) {
        try {
            remindOmissionService.updateRemind(remindOmissionDepartmentIds);
        } catch (Exception e) {
            log.warn("修改失败: " + e.getMessage());
            return new Result(true, StatusCode.ERROR, "修改失败");
        }
        return new Result(true, StatusCode.OK, "修改成功");
    }

}
