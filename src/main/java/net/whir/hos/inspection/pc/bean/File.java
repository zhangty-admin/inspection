package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: zty
 * @Date: 2020/4/26 4:28 下午
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "FILE")
@ApiModel(value = "文件存储")
public class File implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "文件创建时间")
    private String createTime;

    @ApiModelProperty(value = "二进制文件")
    private byte[] files;

    @ApiModelProperty(value = "文件类型")
    private Long type;

    @ApiModelProperty(value = "特殊事件ID")
    private Long specialId;

    @ApiModelProperty(value = "文件标题名称")
    private String title;

}
