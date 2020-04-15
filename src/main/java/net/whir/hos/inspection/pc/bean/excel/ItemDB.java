package net.whir.hos.inspection.pc.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;

/**
 * UserDB:数据库导入(导入的列可以自定义,也可以全部导入)
 * 一般能把所有表格数据都导入,也可以选择性的
 * <p>
 * 数据库需要的数据写在这个实体类,其他需要处理字段属性在业务层写就可以了
 * <p>
 *
 * @author zty
 * @date: 2020/4/14 1:42 下午
 */
@Data
public class ItemDB extends BaseRowModel implements Serializable {

    //说明:这个是excel导入到数据库的实体,所以字段属性是数据库的下划线的形式


    /**
     * 用户编号 数据库列字段,一一对应就行
     * index = 0 表示表格第一列,下面依次类推
     */
    @ExcelProperty(index = 0)
    private String name;

    //如果还需要导入列的信息在这里继续写就行了,我这个示例是把表格所有的字段都导入的数据库
    // 如果有的表格的列不需要导入,那么就直接把上面写的属性删掉一些即可


}
