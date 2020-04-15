package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
@Table(name = "EMPLOYEE")
@ApiModel(value = "人员信息")
public class Employee implements Serializable {

    @Id
    @ApiModelProperty(value = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "性别（0/男，1/女）")
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

    @ApiModelProperty(hidden = true)
    private Department department;

    @ApiModelProperty(hidden = true)
    private Inspection inspection;

}
