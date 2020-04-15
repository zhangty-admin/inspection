package net.whir.hos.inspection.pc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.whir.hos.inspection.pc.bean.InspectionItem;
import net.whir.hos.inspection.pc.bean.Item;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.dao.InspectionItemDao;
import net.whir.hos.inspection.pc.dao.ItemDao;
import net.whir.hos.inspection.pc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/10 9:34 上午
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private InspectionItemDao inspectionItemDao;

    /**
     * 导入检查项数据
     *
     * @param items
     */
    @Override
    public void add(List<Item> items) {
        for (Item item : items) {
            itemDao.insertSelective(item);
        }
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
            Example example = new Example(InspectionItem.class);
            example.createCriteria().andEqualTo("itemId", id);
            inspectionItemDao.deleteByExample(example);
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
    public Example createExample(PageRequest<Item> pageRequest) {
        Item item = pageRequest.getObj();
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
            criteria.andEqualTo("createTime", item.getCreateTime());
        }

        return example;
    }
}
