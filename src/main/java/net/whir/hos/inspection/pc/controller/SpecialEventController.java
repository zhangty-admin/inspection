package net.whir.hos.inspection.pc.controller;

import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.*;
import net.whir.hos.inspection.pc.bean.Files;
import net.whir.hos.inspection.pc.bean.SpecialEvent;
import net.whir.hos.inspection.pc.bean.SpecialEventFile;
import net.whir.hos.inspection.pc.service.FileService;
import net.whir.hos.inspection.pc.service.SpecialEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author: zty
 * @Date: 2020/4/26 2:52 下午
 */

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/SpecialEvent")
@Api(description = "特殊事件")
public class SpecialEventController {

    @Autowired
    private SpecialEventService specialEventService;
    @Autowired
    private FileService fileService;

    @ApiOperation(value = "添加特殊事件")
    @PostMapping("/add")
    public Result insertSpecialEvent(@RequestBody SpecialEventFile specialEventFile) throws Exception {
        //特殊事件添加
        specialEventService.insertSpecialEvent(specialEventFile);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /*@ApiOperation(value = "上传图片")
    @PostMapping("/upload")
    private Result uploadSpecialEvent(@RequestBody SpecialEventFile specialEventFile) {
        //图片上传 url保存数据库
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        for (Files files : specialEventFile.getFile()) {
            MultipartFile multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(files.getFiles());
            String empPhotoUrl = GetPhotoUrl.getEmpPhotoUrl(multipartFile, imgUrl, port);
            Files build = Files.builder()
                    .files(empPhotoUrl).title(files.getTitle()).specialId(specialEventFile.getSpecialEvent().getId()).createTime(format)
                    .build();
            fileService.insert(build);
        }
        return new Result(true, StatusCode.OK, "添加成功");
    }*/


    @ApiOperation(value = "删除特殊事件")
    @DeleteMapping("/delete")
    private Result deleteSpecialEvent(@RequestBody List<Long> ids) {
        try {
            specialEventService.deleteByIds(ids);
        } catch (Exception e) {
            log.warn("删除失败: " + e.getMessage());
            return new Result(false, StatusCode.ERROR, "删除失败");
        }
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @ApiOperation(value = "分页查看特殊事件")
    @PostMapping("/findPage")
    private Result findPage(@RequestBody PageRequest<SpecialEvent> pageRequest) {
        PageResult pageResult = null;
        try {
            Page<SpecialEvent> specialEventPage = specialEventService.findPage(pageRequest);
            pageResult = new PageResult(specialEventPage.getTotal(), specialEventPage.getPages(), specialEventPage.getPageNum()
                    , specialEventPage.getPageSize(), specialEventPage.getResult());
        } catch (Exception e) {
            log.warn("查询失败: " + e.getMessage());
            return new Result(false, StatusCode.ERROR, "查询失败");
        }
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    @ApiOperation(value = "根据特殊事件ID 查询详细信息")
    @GetMapping(value = "/image")
    public Result getImage(@RequestParam Long id) throws Exception {
        List<String> bytesList = new ArrayList<>();

        List<Files> file = fileService.selectById(id);
        for (Files file1 : file) {
            bytesList.add(file1.getFiles());
        }
        return new Result(true, StatusCode.OK, "查询成功", bytesList);
    }


    @ApiOperation(value = "根据ID查询特殊事件(bol true/是小程序 false/pc)")
    @GetMapping(value = "/findSpecialEventById")
    private Result findSpecialEventById(@RequestParam Long id, @RequestParam Boolean bool) {
        SpecialEvent specialEvent = specialEventService.findSpecialEventById(id, bool);
        return new Result(true, StatusCode.OK, "查询成功", specialEvent);
    }


}
