package net.whir.hos.inspection.security.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.security.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: zty
 * @Date: 2020/5/22 9:56 上午
 */

@RestController
@CrossOrigin
@RequestMapping("/upLoad")
@Api(description = "上传")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @ApiOperation(value = "上传图片")
    @PostMapping
    public Result upLoad(@RequestParam("file") MultipartFile file, @RequestParam("empId") Long empId) {
        uploadService.upLoad(file, empId);
        return new Result(true, StatusCode.OK, "注册成功");
    }
}
