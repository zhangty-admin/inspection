package net.whir.hos.inspection.pc.controller;

import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.PageResult;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.pc.bean.Inspection;
import net.whir.hos.inspection.pc.bean.InspectionItemIds;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.service.InspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: zty
 * @Date: 2020/4/9 4:38 下午
 */
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/inspection")
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
        try {
            inspectionService.delete(ids);
        } catch (Exception e) {
            return new Result(false, StatusCode.ERROR, "删除失败: " + e.getMessage());
        }
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @ApiOperation(value = "分页查询巡检计划")
    @PostMapping("/findPage")
    private Result findPage(@RequestBody PageRequest<Inspection> pageRequest) {
        Page<Inspection> page = inspectionService.findPage(pageRequest);
        PageResult<Inspection> pageResult = new PageResult<>(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), page.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

}
