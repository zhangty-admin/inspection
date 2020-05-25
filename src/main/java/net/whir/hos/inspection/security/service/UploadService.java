package net.whir.hos.inspection.security.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: zty
 * @Date: 2020/5/22 10:03 上午
 */
public interface UploadService {

    /**
     * 上传图片
     * @param file
     * @param empId
     */
    void upLoad(MultipartFile file, Long empId);
}
