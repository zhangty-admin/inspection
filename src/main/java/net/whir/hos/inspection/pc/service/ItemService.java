package net.whir.hos.inspection.pc.service;

import com.alibaba.excel.metadata.BaseRowModel;
import com.github.pagehelper.Page;
import net.whir.hos.inspection.pc.bean.Item;
import net.whir.hos.inspection.commons.entity.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/10 9:34 上午
 */
public interface ItemService {

    /**
     * 导入 Excel（一个 sheet）到数据库
     *
     * @param excel       需要导入的excel
     * @param headLineNum
     * @return
     */
    void add(MultipartFile excel, BaseRowModel rowModel, int headLineNum, long empId);

    /**
     * 分页查询检查项信息
     *
     * @param pageRequest
     * @return
     */
    Page<Item> findPage(PageRequest pageRequest);

    /**
     * 批量删除/单独删除
     *
     * @param ids
     * @return
     */
    void deleteByIds(List<Long> ids);

    /**
     * 修改检查项
     *
     * @param item
     */
    void update(Item item);

    /**
     * 查询全部检查项信息
     *
     * @return
     */
    List<Item> findAll();
}
