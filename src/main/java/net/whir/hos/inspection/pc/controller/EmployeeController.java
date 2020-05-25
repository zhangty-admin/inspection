package net.whir.hos.inspection.pc.controller;

import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.PageResult;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.pc.bean.Employee;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.EmployeeInspectionIds;
import net.whir.hos.inspection.pc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


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
    @PostMapping("/page")
    public Result findPage(@RequestBody PageRequest<Employee> pageRequest) {
        Page<Employee> auditPage = employeeService.findPage(pageRequest.getObj(), pageRequest.getPageNum(), pageRequest.getPageSize());
        PageResult pageResult = new PageResult(auditPage.getTotal(), auditPage.getPages(), auditPage.getPageNum(), auditPage.getPageSize(), auditPage.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    @ApiOperation(value = "新增用户信息")
    @PostMapping(value = "/add", consumes = "application/json")
    public Result addEmployee(@RequestBody EmployeeInspectionIds employeeInspectionIds) {
        employeeService.insertEmployee(employeeInspectionIds);
        return new Result(true, StatusCode.OK, "注册成功");
    }

    @ApiOperation(value = "修改用户信息/审核用户信息")
    @PutMapping("/update")
    public Result update(@RequestBody EmployeeInspectionIds employeeInspectionIds) {
        employeeService.updateByEmployee(employeeInspectionIds);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        employeeService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @ApiOperation(value = "查询全部用户")
    @PostMapping("/findAll")
    private Result findAllEmployee(@RequestBody Employee employee) {
        List<Employee> employees = employeeService.findAll(employee);
        return new Result(true, StatusCode.OK, "查询成功", employees);
    }

    @ApiOperation(value = "审批通知人员")
    @GetMapping("/sendAdminApprove")
    private Result sendAdminApprove(@RequestParam Boolean review, @RequestParam Long empId) {
        employeeService.sendAdminApprove(review, empId);
        return new Result();
    }

    /**
     * 测试图片上传
     *
     * @return
     */
    @PostMapping
    public Map<String, String> redisDemo(MultipartFile file) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式  HH:mm:ss
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        String path = "C:/var/uploaded_files/" + date + "/";
        UUID uuid = UUID.randomUUID();
        String originalFilename = file.getOriginalFilename();
        // String fileName = uuid.toString() + originalFilename;
        String extendName = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
        String fileName = uuid.toString() + extendName;
        File dir = new File(path, fileName);
        File filepath = new File(path);
        if (!filepath.exists()) {
            filepath.mkdirs();
        }
        try {
            file.transferTo(new File(dir.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> map = new HashMap<>();
        map.put("filePath", path);
        map.put("fileName", fileName);

        return map;
    }

}
