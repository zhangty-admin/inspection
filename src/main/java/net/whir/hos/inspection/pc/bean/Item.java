package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: zty
 * @Date: 2020/4/10 9:22 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ITEM")
@ApiModel(description = "检查项")
public class Item implements Serializable {
    @Id
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "检查项名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "创建人ID")
    private Long founder;

}
