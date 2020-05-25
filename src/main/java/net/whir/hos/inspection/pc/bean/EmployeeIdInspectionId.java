package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/24 3:30 下午
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "INSPECTION_EMPLOYEE")
@ApiModel(value = "人员ID和巡检计划ID")
public class EmployeeIdInspectionId {

    @Id
    @ApiModelProperty(value = "主键自增ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "巡检计划ID")
    private Long inspectionId;

    @ApiModelProperty(value = "人员ID")
    private Long employeeId;

}
