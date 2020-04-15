package net.whir.hos.inspection.pc.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * @Author: zty
 * @Date: 2020/4/14 3:19 下午
 */

@Data
public class ExcelEntity extends BaseRowModel {

    @ExcelProperty(index = 0 , value = "检查项名称")
    private String name;

}
