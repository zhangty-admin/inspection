package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: zty
 * @Date: 2020/4/9 8:39 下午
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "INSPECTION_ITEM")
@ApiModel(value = "巡检计划与检查项中间表")
public class InspectionItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "巡检计划ID")
    private Long inspectionId;

    @ApiModelProperty(value = "检查项ID")
    private Long itemId;
}
