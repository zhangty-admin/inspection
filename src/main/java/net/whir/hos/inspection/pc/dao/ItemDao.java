package net.whir.hos.inspection.pc.dao;

import net.whir.hos.inspection.pc.bean.Item;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/10 9:35 上午
 */
public interface ItemDao extends Mapper<Item> {

    @Select(value = "select ii.item_id from inspection i inner join inspection_item ii on i.id = ii.inspection_id\n" +
            "where i.id = #{obj}")
    List<Item> selectByInspectionId(Long obj);

    @Select(value = "select i.* from inspection_history ih inner join item i on i.id = ih.item_id where ih.employee_id = #{empId} and ih.inspection_id = #{insId}")
    List<Item> selectItemsByEmpIdAndItemId(Long empId, Long insId);
}
