package net.whir.hos.inspection.pc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.pc.bean.Building;
import net.whir.hos.inspection.pc.service.BuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Author: zty
 * @Date: 2020/4/14 10:35 上午
 */

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/build")
@Api(description = "楼宇操作")
public class BuildController {

    @Autowired
    private BuildService buildService;

    @GetMapping
    @ApiOperation(value = "查询全部楼宇")
    private Result findAll() {
        List<Building> buildings = buildService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", buildings);
    }

}
