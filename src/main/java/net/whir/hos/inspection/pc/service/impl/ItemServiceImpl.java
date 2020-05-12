package net.whir.hos.inspection.pc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.User;
import net.whir.hos.inspection.pc.bean.InspectionItem;
import net.whir.hos.inspection.pc.bean.Item;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.dao.InspectionItemDao;
import net.whir.hos.inspection.pc.dao.ItemDao;
import net.whir.hos.inspection.pc.service.ItemService;
import net.whir.hos.inspection.security.dao.UserDao;
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
@Slf4j
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private InspectionItemDao inspectionItemDao;

    @Autowired
    private UserDao userDao;


    /**
     * 导入 Excel（一个 sheet）到数据库
     * 这里可以处理很多业务逻辑,比如传入的id不能重复,或者把插入失败的数据返回给客户端
     *
     * @param excel       需要导入的excel
     * @param headLineNum
     * @return
     */
    /*@Override
    public void add(MultipartFile excel, BaseRowModel rowModel, int headLineNum, long empId) {
        if (excel == null) {
            log.error("请传入正确的excel格式");
        }

        List<Object> list = ExcelUtil.readExcel(excel, rowModel, headLineNum);
        Item item = new Item();
        for (int i = 0; i < list.size(); i++) {
            ItemDB itemDB = (ItemDB) list.get(i);
            item.setName(itemDB.getName());
            item.setCreateTime(new Date().toString());
            item.setFounder(empId);
            itemDao.insert(item);
            item.setId(null);
        }
    }*/

    /**
     * 导入的EXCle
     * @param items
     */
    @Override
    public void add(List<Item> items) {
        for (Item item : items) {
            itemDao.insert(item);
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
        for (Item item : items) {
            User user = userDao.selectByPrimaryKey(item.getFounder());
            item.setUser(user);
        }
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
     *
     * @param item
     */
    @Override
    public void update(Item item) {
        itemDao.updateByPrimaryKeySelective(item);
    }

    /**
     * 查询全部检查项信息
     *
     * @return
     */
    @Override
    public List<Item> findAll() {
        Example example = new Example(Item.class);
        example.setOrderByClause("id desc");
        return itemDao.selectByExample(example);
    }


    //分页查询条件
    public Example createExample(PageRequest<Item> pageRequest) {
        Item item = pageRequest.getObj();
        Example example = new Example(Item.class);
        example.setOrderByClause("id desc");
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
