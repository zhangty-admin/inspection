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
@ApiModel(value = "人员ID和巡检计划ID")
public class EmployeeInspectionId {

    @ApiModelProperty(value = "人员ID")
    private Long empId;

    @ApiModelProperty(value = "巡检计划ID")
    private Long insId;

}
