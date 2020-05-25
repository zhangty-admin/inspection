package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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

    @ApiModelProperty(value = "照片beast64")
    private String photo;

    @ApiModelProperty(value = "审核（0/false，1/true）")
    private Integer review;

    @ApiModelProperty(value = "是否是负责人（0/false，1/true）")
    private Integer isPrincipal;

    @ApiModelProperty(value = "工号")
    private String jobNumber;

    @ApiModelProperty(value = "微信ID")
    private String wxId;

    @ApiModelProperty(hidden = true)
    private Department department;

    @ApiModelProperty(hidden = true)
    private Inspection inspection;

    @ApiModelProperty(hidden = true)
    private List<Item> items;

}
