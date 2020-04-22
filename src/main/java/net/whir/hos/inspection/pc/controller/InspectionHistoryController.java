package net.whir.hos.inspection.pc.controller;

import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.commons.entity.PageResult;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.pc.bean.Employee;
import net.whir.hos.inspection.pc.bean.Inspection;
import net.whir.hos.inspection.pc.bean.InspectionHistory;
import net.whir.hos.inspection.pc.service.InspectionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zty
 * @Date: 2020/4/20 4:27 下午
 */

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/historyInspection")
@Api(description = "历史巡检")
public class InspectionHistoryController {

    @Autowired
    private InspectionHistoryService historyInspectionService;

    @ApiOperation(value = "历史巡检分页查询")
    @PostMapping("/findPage")
    private Result findPageInspection(@RequestBody PageRequest<Inspection> pageRequest) {
        PageResult page;
        try {
            page = historyInspectionService.findPage(pageRequest);
        } catch (Exception e) {
            log.warn("分页查询失败: " + e.getMessage());
            return new Result(false, StatusCode.ERROR, "查询失败");
        }
        return new Result(true, StatusCode.OK, "查询成功", page);
    }

    @ApiOperation(value = "历史巡检根据巡检ID分页检查项")
    @PostMapping("/findItemsById")
    private Result findItemsById(@RequestBody PageRequest<Long> pageRequest) {
        PageResult pageResult = null;
        try {
            Page<Employee> page = historyInspectionService.findItemsById(pageRequest);
            pageResult = new PageResult(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), page.getResult());
        } catch (Exception e) {
            log.warn("分页查询失败: " + e.getMessage());
            return new Result(false, StatusCode.ERROR, "查询失败");
        }
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

}
