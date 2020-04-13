package net.whir.hos.inspection.pc.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
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

/**
 * @Author: zty
 * @Date: 2020/4/7 10:14 上午
 */

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "employee")
@ApiModel(value = "Employee/人员信息")
public class Employee implements Serializable {

    @Id
    @ApiModelProperty(value = "ID")
    @Excel(name = "工号", orderNum = "6")
    private Long id;

    @ApiModelProperty(value = "名称")
    @Excel(name = "姓名", orderNum = "1")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "性别（0/男，1/女）")
    @Excel(name = "性别", replace = {"男_0", "女_1"}, orderNum = "2")
    private Integer sex;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "部门ID")
    private Long departmentId;

    @ApiModelProperty(value = "区域ID")
    private Long inspectionId;

    @ApiModelProperty(value = "照片ID")
    private Long photoId;

    @ApiModelProperty(value = "是否通过")
    private Integer review;

    @ApiModelProperty(value = "部门信息")
    private Department department;

    @ApiModelProperty(value = "巡检计划")
    private Inspection inspection;

}
