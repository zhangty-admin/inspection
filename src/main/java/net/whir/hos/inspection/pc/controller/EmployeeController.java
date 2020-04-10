package net.whir.hos.inspection.pc.controller;

import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.PageResult;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.pc.bean.Employee;
import net.whir.hos.inspection.pc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @Author: zty
 * @Date: 2020/4/7 10:14 上午
 */
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/employee")
@Api(description = "用户操作")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @ApiOperation(value = "分页条件查询用户信息")
    @GetMapping("/page/{page}/{size}")
    public Result findPage(@RequestParam Map map, @PathVariable int page, @PathVariable int size) {
        Page<Employee> auditPage = employeeService.findPage(map, page, size);
        PageResult pageResult = new PageResult(auditPage.getTotal(), auditPage.getPages(), auditPage.getPageNum(), auditPage.getPageSize(), auditPage.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    // TODO 新增用户展示没有  回头补上
    @ApiOperation(value = "新增用户信息")
    @PostMapping("/add")
    public Result add() {
        return new Result();
    }

    @ApiOperation(value = "修改用户信息/审核用户信息")
    @PutMapping("/update")
    public Result update(@RequestBody Employee employee) {
        employeeService.updateByEmployee(employee);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        employeeService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}