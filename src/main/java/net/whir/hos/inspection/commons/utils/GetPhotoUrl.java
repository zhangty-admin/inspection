package net.whir.hos.inspection.commons.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

/**
 * @Author: zty
 * @Date: 2020/5/25 9:23 上午
 */
public class GetPhotoUrl {

    /**
     * 保存图片 返回url
     *
     * @param file
     * @param path
     * @param port
     * @return
     */
    public static String getEmpPhotoUrl(MultipartFile file, String path, String port) throws IOException {
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
        InetAddress address = null;
        file.transferTo(new File(dir.getAbsolutePath()));
        address = InetAddress.getLocalHost();

        String hostAddress = address.getHostAddress();
        return hostAddress + ":" + port + "/" + path + fileName;
    }

}
