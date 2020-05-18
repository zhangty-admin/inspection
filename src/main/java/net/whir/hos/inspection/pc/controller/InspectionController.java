package net.whir.hos.inspection.pc.controller;

import com.github.pagehelper.Page;
import com.google.zxing.WriterException;
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
import net.whir.hos.inspection.commons.utils.QRCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @ApiOperation(value = "查询全部巡检计划")
    @GetMapping("/findAll")
    private Result findAll() {
        List<Inspection> inspections = inspectionService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", inspections);
    }

    @ApiOperation(value = "下载")
    @PostMapping("/code")
    private Result generateQRCode() {
        try {
            boolean flag = QRCodeUtil.generateCode("http://kangkang.vaiwan.com/", "3", "巡检3的撒大苏打");
            if (flag) {
                System.out.println("成功生成二维码");
            }
        } catch (WriterException | IOException e) {
            log.warn("生成二维码失败: /r/n" + e.getMessage());
            return new Result(false, StatusCode.ERROR, "生成二维码失败");
        }
        return new Result(true, StatusCode.OK, "成功生成二维码");
    }

}
