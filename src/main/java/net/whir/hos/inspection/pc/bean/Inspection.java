package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.whir.hos.inspection.commons.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/9 4:42 下午
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "INSPECTION")
@ApiModel(value = "巡检计划")
public class Inspection implements Serializable {

    @Id
    @ApiModelProperty(value = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "巡检计划名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "编号")
    private String numbering;

    @ApiModelProperty(value = "楼宇")
    private String building;

    @ApiModelProperty(value = "楼层")
    private String floor;

    @ApiModelProperty(value = "应查天数")
    private Integer heaven;

    @ApiModelProperty(value = "每天应查频率")
    private Integer frequency;

    @ApiModelProperty(value = "二维码路径")
    private String codeUrl;

    @ApiModelProperty(value = "科室ID")
    private Long departmentId;

    @ApiModelProperty(value = "负责人ID")
    private Long employeeId;

    @ApiModelProperty(value = "创建人ID")
    private Long founder;


    @Transient
    @ApiModelProperty(value = "结束日期")
    private String endTime;

    @Transient
    @ApiModelProperty(value = "实查天数")
    private Integer actualDays;

    @ApiModelProperty(hidden = true)
    private Department department;

    @ApiModelProperty(hidden = true)
    private List<Item> item;

    @ApiModelProperty(hidden = true)
    private Employee employee;

    @ApiModelProperty(hidden = true)
    private User user;

}
