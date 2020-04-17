package net.whir.hos.inspection.utils;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;


public class QRCodeUtil {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public static boolean generateCode(String url, String productId, String InspectionName) throws WriterException, IOException {
        // 这里是URL，扫描之后就跳转到这个界面
        String text = url + productId;


        String path = "E:/"; // 图片生成的位置
        int width = 500;
        int height = 500;
        // 二维码图片格式
        String format = "jpg";
        // 设置编码，防止中文乱码
        Hashtable<EncodeHintType, Object> ht = new Hashtable<EncodeHintType, Object>();
        ht.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 设置二维码参数(编码内容，编码类型，图片宽度，图片高度,格式)
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, ht);

        // 生成二维码(定义二维码输出服务器路径)
        File outputFile = new File(path);
        if (!outputFile.exists()) {
            // 创建文件夹
            outputFile.mkdir();
        }

        int b_width = bitMatrix.getWidth();
        int b_height = bitMatrix.getHeight();

        // 建立图像缓冲器
        BufferedImage image = new BufferedImage(b_width, b_height, BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < b_width; x++) {
            for (int y = 0; y < b_height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
            }
        }

        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.WHITE);//设置颜色
        // 抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 计算文字长度，计算居中的x点坐标
        Font font = new Font("黑体", Font.PLAIN, 20);
        FontMetrics fm = g2.getFontMetrics(font);
        int textWidth = fm.stringWidth(InspectionName);
        int widthX = (width - textWidth) / 2;
        g2.setFont(font);//设置字体
        g2.setColor(Color.BLACK);//设置颜色
        g2.drawString(InspectionName, widthX, 480);

        // 生成二维码
        ImageIO.write(image, format, new File(path + "/code." + format));
        // 二维码的名称
        // code.jpg

        return true;
    }

}