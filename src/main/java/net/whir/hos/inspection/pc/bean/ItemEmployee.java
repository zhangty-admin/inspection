package net.whir.hos.inspection.pc.bean;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/21 5:39 下午
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "检查项和历史巡检人员")
public class ItemEmployee implements Serializable {

    @ApiModelProperty(value = "检查项")
    private List<Item> items;

    @ApiModelProperty(value = "历史巡检人员")
    private Page<Employee> employee;

}
