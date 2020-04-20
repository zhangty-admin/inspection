package net.whir.hos.inspection.pc.controller;

import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.*;
import net.whir.hos.inspection.pc.bean.RemindAdmin;
import net.whir.hos.inspection.pc.service.AdminRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: zty
 * @Date: 2020/4/14 1:42 下午
 */

@RestController
@Slf4j
@CrossOrigin
@Api(description = "提醒时间")
@RequestMapping("/adminRemind")
public class RemindAdminController {

    @Autowired
    private AdminRemindService remindService;

    @ApiOperation(value = "分页查询管理员提醒")
    @PostMapping("/findPage")
    private Result findPageAdminRemind(@RequestBody PageRequest<RemindAdmin> remindPageRequest) {
        Page<RemindAdmin> page = remindService.findPage(remindPageRequest);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<RemindAdmin>(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), page.getResult()));
    }

    @ApiOperation(value = "新增提醒管理员")
    @PostMapping("/add")
    private Result addAdminRemind(@RequestBody RemindAdmin adminRemind) {
        try {
            remindService.add(adminRemind);
        } catch (Exception e) {
            log.warn("添加失败: " + e.getMessage());
            return new Result(true, StatusCode.ERROR, "添加失败");
        }
        return new Result(true, StatusCode.OK, "添加成功");
    }
}
