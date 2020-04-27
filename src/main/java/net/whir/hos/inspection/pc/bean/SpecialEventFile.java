package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/27 10:17 上午
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "特殊事件和文件上传")
public class SpecialEventFile {

    @ApiModelProperty(value = "特殊事件")
    private SpecialEvent specialEvent;

    @ApiModelProperty(value = "文件")
    private File file;

    @ApiModelProperty(value = "图片和语音")
    private MultipartFile[] files;

}
