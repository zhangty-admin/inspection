package net.whir.hos.inspection.pc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.pc.bean.Floor;
import net.whir.hos.inspection.pc.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/14 11:05 上午
 */

@RestController
@CrossOrigin
@Slf4j
@Api(description = "楼层")
@RequestMapping("/floor")
public class FloorController {

    @Autowired
    private FloorService floorService;

    @GetMapping
    @ApiOperation(value = "查询全部楼层")
    private Result findFloorAll() {
        List<Floor> floors = floorService.findFloorAll();
        return new Result(true, StatusCode.OK, "查询成功", floors);
    }

}
