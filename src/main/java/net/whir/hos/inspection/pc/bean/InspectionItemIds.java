package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/9 4:42 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "巡检计划和检查项ID")
public class InspectionItemIds implements Serializable {

    @ApiModelProperty(value = "巡检计划")
    private Inspection inspection;

    @ApiModelProperty(value = "检查项ID")
    private List<Long> itemIds;
}
