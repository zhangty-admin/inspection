package net.whir.hos.inspection.pc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.pc.bean.Item;
import net.whir.hos.inspection.pc.bean.PageRequest;
import net.whir.hos.inspection.pc.dao.ItemDao;
import net.whir.hos.inspection.pc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/10 9:34 上午
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    /**
     * 导入检查项数据
     *
     * @param item
     */
    @Override
    public void add(Item item) {
        itemDao.insert(item);
    }

    /**
     * 分页查询检查项信息
     *
     * @param pageRequest
     * @return
     */
    @Override
    public Page<Item> findPage(PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        Example example = createExample(pageRequest);
        List<Item> items = itemDao.selectByExample(example);
        return (Page<Item>) items;
    }

    /**
     * 批量删除/单独删除
     *
     * @param ids
     * @return
     */
    @Override
    public void deleteByIds(List<Long> ids) {
        for (Long id : ids) {
            itemDao.deleteByPrimaryKey(id);
        }
    }

    /**
     * 修改检查项
     * @param item
     */
    @Override
    public void update(Item item) {
        itemDao.updateByPrimaryKeySelective(item);
    }


    //分页查询条件
    public Example createExample(PageRequest pageRequest) {
        Item item = pageRequest.getItem();
        Example example = new Example(Item.class);
        Example.Criteria criteria = example.createCriteria();
        if (item == null) {
            return example;
        }

        //name
        if (item.getName() != null && !"".equals(item.getName())) {
            criteria.andLike("name", "%" + item.getName() + "%");
        }

        //createTime
        if (item.getCreateTime() != null && !"".equals(item.getCreateTime())) {
            criteria.andEqualTo("create_time", item.getCreateTime());
        }

        return example;
    }
}
