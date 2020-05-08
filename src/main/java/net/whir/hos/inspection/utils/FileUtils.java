package net.whir.hos.inspection.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    /**
     * @param file     文件
     * @param path     文件存放路径
     * @param fileName 保存的文件名
     * @return
     */
    public static boolean upload(MultipartFile file, String path, String fileName) {

        //确定上传的文件名
        String realPath = path + "/" + fileName;
        System.out.println("上传文件：" + realPath);

        File dest = new File(realPath);
        File file1 = new File(path);

        //判断文件父目录是否存在
        if (!file1.exists()) {
//            dest.mkdir();
            file1.mkdir();
        }

        try {
            //保存文件
            byte[] bytes = file.getBytes();
            FileOutputStream fos = new FileOutputStream(dest);
            fos.write(bytes);
            fos.flush();
            fos.close();
//            file.transferTo(dest);
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }

}


