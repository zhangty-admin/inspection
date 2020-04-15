package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: zty
 * @Date: 2020/4/14 2:35 下午
 */

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "REMIND")
@ApiModel(value = "提醒时间")
public class Remind implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "提醒时间")
    private String remindTime;

    @ApiModelProperty(value = "用户ID")
    private Long userId;
}
