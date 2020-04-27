package net.whir.hos.inspection.pc.controller;

import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.*;
import net.whir.hos.inspection.pc.bean.File;
import net.whir.hos.inspection.pc.bean.SpecialEvent;
import net.whir.hos.inspection.pc.bean.SpecialEventFile;
import net.whir.hos.inspection.pc.service.FileService;
import net.whir.hos.inspection.pc.service.SpecialEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    @PostMapping("/add/{multipartFiles}")
    public Result insert(@RequestBody SpecialEventFile specialEventFile,@PathVariable List<MultipartFile> multipartFiles) throws Exception {
        try {
            //特殊事件添加
            SpecialEvent specialEvent = specialEventFile.getSpecialEvent();
            specialEventService.insert(specialEvent);

            //图片上传 转beat64保存数据库
            for (MultipartFile multipartFile : multipartFiles) {
                File file = specialEventFile.getFile();
                InputStream inputStream = multipartFile.getInputStream();
                byte[] pictureData = new byte[(int) multipartFile.getSize()];
                inputStream.read(pictureData);
                file.setFiles(pictureData);
                file.setTitle(multipartFile.getOriginalFilename());
                file.setSpecialId(specialEvent.getId());
                fileService.insert(file);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(true, StatusCode.OK, "添加成功");
    }


    @ApiOperation(value = "删除特殊事件")
    @PostMapping("/delete")
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
    @PostMapping(value = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public Result getImage(@PathVariable Long id) throws Exception {
        List<byte[]> bytesList = new ArrayList<>();

        byte[] bytes;
        List<File> file = fileService.selectById(id);
        for (File file1 : file) {
            bytesList.add(file1.getFiles());
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new Result(true, StatusCode.OK, "查询成功", bytesList);
    }





}
