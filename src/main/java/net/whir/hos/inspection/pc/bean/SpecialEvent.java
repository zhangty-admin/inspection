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
 * @Date: 2020/4/26 2:54 下午
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "SPECIAL_EVENT")
@ApiModel(value = "特殊时间")
public class SpecialEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "上报日期")
    private String createTime;

    @ApiModelProperty(value = "上报人ID")
    private Long employeeId;

    @ApiModelProperty(value = "部门ID")
    private Long departmentId;

    @ApiModelProperty(value = "巡检计划ID")
    private Long InspectionId;

    @ApiModelProperty(value = "特殊事件说明")
    private String explanation;

    @ApiModelProperty(hidden = true)
    private Employee employee;

    @ApiModelProperty(hidden = true)
    private Department department;

    @ApiModelProperty(hidden = true)
    private Inspection inspection;

    @Transient
    @ApiModelProperty(value = "结束时间")
    private String endTime;

}
