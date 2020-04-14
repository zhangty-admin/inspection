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
@ApiModel(description = "楼层")
public class floor implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @ApiModelProperty(name = "楼层名称")
    private String name;

    @ApiModelProperty(name = "创建时间")
    private Date createTime;

    @ApiModelProperty(name = "楼宇ID")
    private Integer building;

}
