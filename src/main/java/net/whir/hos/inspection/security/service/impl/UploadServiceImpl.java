package net.whir.hos.inspection.security.service.impl;

import net.whir.hos.inspection.pc.bean.Employee;
import net.whir.hos.inspection.pc.dao.EmployeeDao;
import net.whir.hos.inspection.security.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * @Author: zty
 * @Date: 2020/5/22 9:59 上午
 */

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private EmployeeDao employeeDao;

    /**
     * 上传图片
     *
     * @param file
     * @param empId
     */
    public void upLoad(MultipartFile file, Long empId) {

    }



}
