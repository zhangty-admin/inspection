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
@ApiModel(value = "统一提醒和部门人ID")
public class RemindUnifiedInspectionIds {

    @ApiModelProperty(value = "统一提醒")
    private RemindUnified unifiedRemind;

    @ApiModelProperty(value = "部门ID")
    private List<Long> inspectionIds;

}
