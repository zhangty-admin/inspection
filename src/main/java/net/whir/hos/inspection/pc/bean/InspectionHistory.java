package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/20 4:29 下午
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "INSPECTION_HISTORY")
@ApiModel(value = "InspectionHistory/历史巡检")
public class InspectionHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "巡检计划ID")
    private Long inspectionId;

    @ApiModelProperty(value = "巡检时间")
    private String createTime;

    @ApiModelProperty(value = "巡检项ID")
    private Long itemId;

    @ApiModelProperty(value = "巡检人ID")
    private Long employeeId;

    @ApiModelProperty(hidden = true)
    private Inspection inspection;

}
