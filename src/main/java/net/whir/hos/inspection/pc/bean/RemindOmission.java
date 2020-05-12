package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/17 2:43 下午
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "遗漏消息提醒")
@Table(name = "REMIND_OMISSION")
public class RemindOmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "提醒时间")
    private String reminderTime;

    @ApiModelProperty(value = "人员ID")
    private Long employeeId;

    @ApiModelProperty(hidden = true)
    private List<Inspection> inspections;

    @ApiModelProperty(hidden = true)
    private Employee employee;

}
