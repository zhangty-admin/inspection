package net.whir.hos.inspection.pc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
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
    private Result add(@RequestBody InspectionItemIds inspectionItemIds) {
        try {
            inspectionService.add(inspectionItemIds);
        } catch (Exception e) {
            log.warn("新增失败:" + e.getMessage());
            return new Result(false, StatusCode.ERROR, "新增失败");
        }
        return new Result(true, StatusCode.OK, "新增成功");
    }

    @ApiOperation(value = "修改巡检计划")
    @PutMapping("/update")
    private Result update(@RequestBody InspectionItemIds inspectionItemIds) {
        try {
            inspectionService.update(inspectionItemIds);
        } catch (Exception e) {
            log.warn("修改失败:" + e.getMessage());
            return new Result(false, StatusCode.ERROR, "修改失败");
        }
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @ApiOperation(value = "删除巡检计划")
    @DeleteMapping("/delete")
    private Result delete(@RequestBody List<Long> ids) {
        inspectionService.delete(ids);
        return new Result();
    }

}
