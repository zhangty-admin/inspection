package net.whir.hos.inspection.pc.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.pc.bean.Inspection;
import net.whir.hos.inspection.pc.bean.InspectionItem;
import net.whir.hos.inspection.pc.bean.InspectionItemIds;
import net.whir.hos.inspection.pc.dao.InspectionDao;
import net.whir.hos.inspection.pc.dao.InspectionItemDao;
import net.whir.hos.inspection.pc.service.InspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/9 4:51 下午
 */
@Service
@Slf4j
@Transactional
public class InspectionServiceImpl implements InspectionService {

    @Autowired
    private InspectionDao inspectionDao;
    @Autowired
    private InspectionItemDao inspectionItemDao;

    /**
     * 新增巡检计划
     *
     * @param inspectionItemIds
     */
    @Override
    public void add(InspectionItemIds inspectionItemIds) {
        List<Long> itemIds = inspectionItemIds.getItemIds();
        Inspection inspection = inspectionItemIds.getInspection();

        InspectionItem inspectionItem = new InspectionItem();
        inspectionDao.insert(inspection);
        //建立和插入关系表操作
        relateAndInsertList(inspectionItemDao, inspectionItem, inspection.getId(), itemIds, "insert");
    }

    /**
     * 修改巡检计划
     *
     * @param inspectionItemIds
     */
    @Override
    public void update(InspectionItemIds inspectionItemIds) {
        List<Long> itemIds = inspectionItemIds.getItemIds();
        Inspection inspection = inspectionItemIds.getInspection();
        if (!StringUtils.isEmpty(itemIds)) {
            for (Long itemId : itemIds) {
                inspectionItemDao.deleteByPrimaryKey(itemId);
            }
            InspectionItem inspectionItem = new InspectionItem();
            relateAndInsertList(inspectionDao, inspectionItem, inspection.getId(), itemIds, "insert");
        }
        inspectionDao.updateByPrimaryKeySelective(inspection);
    }

    /**
     * 删除巡检计划
     *
     * @param ids
     */
    @Override
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            inspectionDao.deleteByPrimaryKey(id);
            Example example = new Example(InspectionItem.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("inspection_id", id);
            inspectionItemDao.deleteByExample(example);
        }
    }


    /**
     * 建立和插入关系表操作
     *
     * @param dao        可以操作的dao
     * @param data       要插入的数据
     * @param productId1 建立关系的id
     * @param productId2 建立关系的id
     * @param model      新增还是创建
     * @param <T>
     */
    private <T> void relateAndInsertList(Object dao, T data, Long productId1, List<Long> productId2, String model) {
        try {
            if (StringUtils.isEmpty(data)) return;
            for (Long aLong : productId2) {
                Method setId = data.getClass().getMethod("setId", Long.class);
                setId.invoke(data, (Long) null);
                Method setInspectionId = data.getClass().getMethod("setInspectionId", Long.class);
                setInspectionId.invoke(data, productId1);
                Method setItemId = data.getClass().getMethod("setItemId", Long.class);
                setItemId.invoke(data, aLong);
                Method insertList = dao.getClass().getMethod(model, Object.class);
                insertList.invoke(dao, data);
            }
        } catch (Exception e) {
            log.warn("创建产品出错:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
