package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Author: zty
 * @Date: 2020/5/12 4:36 下午
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户与照片")
public class EmployeePhone {

    private Long empId;

}
