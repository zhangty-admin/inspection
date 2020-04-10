package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zty
 * @Date: 2020/4/10 9:22 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "分页请求")
public class PageRequest {

    @ApiModelProperty(value = "当前页码")
    private int pageNum;

    @ApiModelProperty(value = "每页数量")
    private int pageSize;

    @ApiModelProperty(value = "查询条件")
    private Item item;

}