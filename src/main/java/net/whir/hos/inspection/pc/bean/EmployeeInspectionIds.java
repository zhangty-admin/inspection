package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/24 3:30 下午
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "人员和巡检计划ID")
public class EmployeeInspectionIds {

    @ApiModelProperty(value = "人员")
    private Employee employee;
    @ApiModelProperty(value = "巡检计划ID")
    private List<Long> insIds;

}
