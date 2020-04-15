package net.whir.hos.inspection.pc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.pc.bean.Remind;
import net.whir.hos.inspection.pc.service.RemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/14 1:42 下午
 */

@RestController
@CrossOrigin
@Api(description = "提醒时间")
@RequestMapping("/remind")
public class RemindController {

    @Autowired
    private RemindService remindService;

    @ApiOperation(value = "分页查询统一提醒")
    @PostMapping("/findPage")
    private Result findPageRemind(@RequestBody PageRequest<Remind> remindPageRequest) {
        List<Remind> remindList = remindService.findPage(remindPageRequest);
        return new Result(true, StatusCode.OK, "查询成功", remindList);
    }

    @ApiOperation(value = "新增统一提醒")
    @PostMapping("/add")
    private Result addRemind(@RequestBody Remind remind) {

        return new Result();
    }

}
