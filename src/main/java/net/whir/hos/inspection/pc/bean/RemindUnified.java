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
 * @Date: 2020/4/14 2:35 下午
 */

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "REMIND_UNIFIED")
@ApiModel(value = "统一提醒时间")
public class RemindUnified implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "提醒时间")
    private String remindTime;

    @ApiModelProperty(hidden = true)
    private List<Inspection> inspections;
}
