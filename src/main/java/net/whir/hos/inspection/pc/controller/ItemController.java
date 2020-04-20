package net.whir.hos.inspection.pc.controller;

import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.PageResult;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.pc.bean.Item;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.excel.ItemDB;
import net.whir.hos.inspection.pc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: zty
 * @Date: 2020/4/10 9:18 上午
 */

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/item")
@Api(description = "检查项目库")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/add")
    @ApiOperation(value = "导入检查项数据")
    /*private Result add(@RequestParam MultipartFile excel, @RequestParam(defaultValue = "1") int headLineNum, @RequestParam(defaultValue = "1") Long empId) {
        try {
            itemService.add(excel, new ItemDB(), headLineNum, empId);
        } catch (Exception e) {
            log.warn("导入失败: " + e.getMessage());
            return new Result(false, StatusCode.ERROR, "导入失败");
        }
        return new Result(true, StatusCode.OK, "导入成功");
    }*/
    private Result add(@RequestBody List<Item> items) {
        try {
            itemService.add(items);
        } catch (Exception e) {
            log.warn("导入失败: " + e.getMessage());
            return new Result(false, StatusCode.ERROR, "导入失败");
        }
        return new Result(true, StatusCode.OK, "导入成功");
    }


    @ApiOperation(value = "分页查询检查项信息")
    @PostMapping("/findPage")
    /*@ApiImplicitParams(
            @ApiImplicitParam(name = "ID", value = "检查项ID", dataType = "Long")
    )*/
    private Result findPage(@RequestBody PageRequest<Item> pageRequest) {
        Page<Item> page = itemService.findPage(pageRequest);
        PageResult<Item> pageResult = new PageResult<>(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), page.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    @ApiOperation(value = "批量删除检查项/单独删除检查项")
    @DeleteMapping("/delete")
    private Result delete(@RequestBody List<Long> ids) {
        try {
            itemService.deleteByIds(ids);
        } catch (Exception e) {
            log.warn("删除失败： " + e.getMessage());
            return new Result(false, StatusCode.ERROR, "删除失败");
        }
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @ApiOperation(value = "修改检查项")
    @PutMapping("/update")
    private Result update(@RequestBody Item item) {
        try {
            itemService.update(item);
        } catch (Exception e) {
            log.warn("修改失败： " + e.getMessage());
            return new Result(false, StatusCode.ERROR, "修改失败");
        }
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @ApiOperation(value = "查询全部检查项信息")
    @GetMapping("/findAll")
    private Result findAllItem() {
        List<Item> items = itemService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", items);
    }

}

