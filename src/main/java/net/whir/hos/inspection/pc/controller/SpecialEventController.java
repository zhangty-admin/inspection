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
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

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
    public Result insert(@RequestBody SpecialEvent specialEvent) throws Exception {
        //特殊事件添加
        specialEventService.insert(specialEvent);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @ApiOperation(value = "上传图片")
    @PostMapping("/upload")
    private Result upload(@RequestParam SpecialEventFile specialEventFile) {
        //图片上传 转beat64保存数据库
//        try {
        for (Files files : specialEventFile.getFile()) {
            files.setFiles(files.getFiles());
            files.setTitle(files.getTitle());
            files.setSpecialId(specialEventFile.getSpecialEvent().getId());
            fileService.insert(files);
        }

            /*for (MultipartFile multipartFile : specialEventFile.getFile()) {
                Files file = new Files();
                InputStream inputStream = multipartFile.getInputStream();
                byte[] pictureData = new byte[(int) multipartFile.getSize()];
                int read = inputStream.read(pictureData);
                file.setFiles(pictureData);
                file.setTitle(multipartFile.getOriginalFilename());
                file.setSpecialId(specialEventFile.getSpecialEvent().getId());
                fileService.insert(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return new Result(true, StatusCode.OK, "添加成功");
    }


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
        List bytesList = new ArrayList<>();

        List<Files> file = fileService.selectById(id);
        for (Files file1 : file) {
            bytesList.add(file1.getFiles());
        }
        return new Result(true, StatusCode.OK, "查询成功", bytesList);
    }


    @ApiOperation(value = "转换")
    @PostMapping(value = "/multipartFiles")
    public Result insert1(@RequestParam MultipartFile multipartFiles) throws Exception {
        try {
            //图片上传 转beat64保存数据库
                String s = multipartFileToBASE64(multipartFiles);
                System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(true, StatusCode.OK, "添加成功");
    }

    // MultipartFile转BASE64字符串
    public static String multipartFileToBASE64(MultipartFile mFile) throws Exception {
        BASE64Encoder bEncoder = new BASE64Encoder();
        String[] suffixArra = mFile.getOriginalFilename().split("\\.");
        String preffix = "data:image/jpg;base64,".replace("jpg", suffixArra[suffixArra.length - 1]);
        String base64EncoderImg = preffix + bEncoder.encode(mFile.getBytes()).replaceAll("[\\s*\t\n\r]", "");
        return base64EncoderImg;
    }

   /* @RequestMapping("/multipleImageUpload")
    public Result multipleImageUpload(@RequestParam("uploadFiles") MultipartFile[] files, Model model, HttpServletRequest request) {
        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                System.out.println("文件为空空");
            }
            try {
                ClassPathResource classPathResource = new ClassPathResource("static");
                String path1 = classPathResource.getPath();
                String fileName = file.getOriginalFilename();  // 文件名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
                String filePath = "D:/temp-rainy/"; // 上传后的路径
                fileName = UUID.randomUUID() + suffixName; // 新文件名
                File dest = new File(filePath + fileName);
                File file1 = new File("D:/temp-rainy");
                if (!file1.exists()) {
                    file1.mkdirs();
                }
                //保存文件
                byte[] bytes = file.getBytes();
                FileOutputStream fos = new FileOutputStream(dest);
                fos.write(bytes);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }*/


}
