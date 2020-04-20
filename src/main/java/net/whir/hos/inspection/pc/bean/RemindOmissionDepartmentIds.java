package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/16 11:03 上午
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "漏检提醒和部门ID")
public class RemindOmissionDepartmentIds {

    @ApiModelProperty(value = "统一提醒")
    private RemindOmission remindOmission;

    @ApiModelProperty(value = "部门ID")
    private List<Long> departmentIds;

}
