package net.whir.hos.inspection.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/16 11:38 上午
 */

@Slf4j
public class MappingTableUtil {

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
    public static <T> void relateMapping(Object dao, T data, Long productId1, List<Long> productId2, String model) {
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
