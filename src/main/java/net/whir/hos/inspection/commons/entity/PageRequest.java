package net.whir.hos.inspection.commons.entity;

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
@ApiModel(value = "分页请求")
public  class PageRequest<T> {

    @ApiModelProperty(value = "当前页码")
    private int pageNum = 1;

    @ApiModelProperty(value = "每页数量")
    private int pageSize = 10;

    @ApiModelProperty(value = "查询条件")
    private T obj;

}