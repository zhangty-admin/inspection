package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zty
 * @Date: 2020/4/13 4:24 下午
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FLOOR")
@ApiModel(value = "楼层")
public class Floor implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "楼层名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "楼宇ID")
    private Integer building;

}
