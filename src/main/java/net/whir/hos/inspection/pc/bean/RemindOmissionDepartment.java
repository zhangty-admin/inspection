package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: zty
 * @Date: 2020/4/16 11:03 上午
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "DEPARTMENT_REMINDOMISSION")
@ApiModel(value = "漏检提醒和部门人员中间表")
public class RemindOmissionDepartment {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "漏检提醒ID")
    @Column(name = "remindOmission_id")
    private Long remindOmissionId;

    @ApiModelProperty(value = "部门ID")
    private Long departmentId;

}
