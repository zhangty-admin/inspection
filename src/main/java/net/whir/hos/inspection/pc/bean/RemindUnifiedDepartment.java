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
@Table(name = "DEPARTMENT_UNIFIEDREMIND")
@ApiModel(value = "统一提醒和部门人员中间表")
public class RemindUnifiedDepartment {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "统一提醒ID")
    @Column(name = "unifiedRemind_id")
    private Long unifiedRemindId;

    @ApiModelProperty(value = "部门ID")
    private Long departmentId;

}
