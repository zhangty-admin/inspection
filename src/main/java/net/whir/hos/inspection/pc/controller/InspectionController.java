package net.whir.hos.inspection.pc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.pc.bean.Inspection;
import net.whir.hos.inspection.pc.bean.InspectionItemIds;
import net.whir.hos.inspection.pc.service.InspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/9 4:38 下午
 */
@RequestMapping("/inspection")
@RestController
@CrossOrigin
@Slf4j
@Api(description = "巡检计划")
public class InspectionController {

    @Autowired
    private InspectionService inspectionService;


    @ApiOperation(value = "新增巡检计划")
    @PostMapping("/add")
    public Result add(@RequestBody InspectionItemIds inspectionItemIds) {
        try {
            inspectionService.add(inspectionItemIds);
        } catch (Exception e) {
            log.warn("新增失败:" + e.getMessage());
            return new Result(false, StatusCode.ERROR, "新增失败");
        }
        return new Result(true, StatusCode.OK, "新增成功");
    }

//    @ApiOperation(value = "sha")

}
