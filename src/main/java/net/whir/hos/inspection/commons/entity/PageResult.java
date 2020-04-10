package net.whir.hos.inspection.commons.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @Author: zty
 * @Date: 2020/4/7 10:14 上午
 * 返回数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "分页数据")
public class PageResult<T> {

    @ApiModelProperty(value = "总记录数")
    private Long total;

    @ApiModelProperty(value = "总页数")
    private Integer pages;

    @ApiModelProperty(value = "当前页面")
    private Integer PageNum;

    @ApiModelProperty(value = "页面大小")
    private Integer PageSize;

    @ApiModelProperty(value = "记录")
    private List<T> rows;
}
