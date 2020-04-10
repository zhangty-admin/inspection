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
        relateAndInsertList(inspectionItemDao, inspectionItem, inspection.getId(), itemIds);
    }


    /**
     * 建立和插入关系表操作
     *
     * @param dao    可以操作的dao
     * @param data   要插入的数据
     * @param productId1 建立关系的id
     */
    private <T>void relateAndInsertList(Object dao, T data, Long productId1, List<Long> productId2) {
        try {
            if (StringUtils.isEmpty(data)) return;
            for (Long aLong : productId2) {
                /*Method setId = data.getClass().getMethod("setId", Long.class);
                setId.invoke(data, (Long) null);*/
                Method setInspectionId = data.getClass().getMethod("setInspectionId", Long.class);
                setInspectionId.invoke(data, productId1);
                Method setItemId = data.getClass().getMethod("setItemId", Long.class);
                setItemId.invoke(data, aLong);
                Method insertList = dao.getClass().getMethod("insert", Object.class);
                insertList.invoke(dao, data);
            }
        } catch (Exception e) {
            log.warn("创建产品出错:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
