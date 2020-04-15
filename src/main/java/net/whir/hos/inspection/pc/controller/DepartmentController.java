package net.whir.hos.inspection.pc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.pc.bean.Department;
import net.whir.hos.inspection.pc.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/15 9:24 上午
 */

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/department")
@Api(description = "部门操作")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "查询全部部门")
    @GetMapping("/findAll")
    private Result findDepartmentAll() {
        List<Department> departments = departmentService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", departments);
    }

}
