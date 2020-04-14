package net.whir.hos.inspection.pc.service;

import com.github.pagehelper.Page;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.pc.bean.Item;
import net.whir.hos.inspection.pc.bean.PageRequest;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/10 9:34 上午
 */
public interface ItemService {

    /**
     * 导入检查项数据
     * @param item
     */
    void add(List<Item> item);

    /**
     * 分页查询检查项信息
     * @param pageRequest
     * @return
     */
    Page<Item> findPage(PageRequest pageRequest);

    /**
     * 批量删除/单独删除
     * @param ids
     * @return
     */
    void deleteByIds(List<Long> ids);

    /**
     * 修改检查项
     * @param item
     */
    void update(Item item);
}
