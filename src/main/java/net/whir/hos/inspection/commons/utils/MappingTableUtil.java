package net.whir.hos.inspection.commons.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/16 11:38 上午
 */

@Slf4j
public class MappingTableUtil {

    /**
     * 建立和插入关系表操作(中间表bean的实体一定要对应 否则不对)
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

                Field[] declaredFields = data.getClass().getDeclaredFields();
                String id = getConstructionMethod(declaredFields[0].getName());
                Method setId = data.getClass().getMethod(id, Long.class);
                setId.invoke(data, (Long) null);

                String product1 = getConstructionMethod(declaredFields[1].getName());
                Method setInspectionId = data.getClass().getMethod(product1, Long.class);
                setInspectionId.invoke(data, productId1);

                String product2 = getConstructionMethod(declaredFields[2].getName());
                Method setItemId = data.getClass().getMethod(product2, Long.class);
                setItemId.invoke(data, aLong);

                Method insertList = dao.getClass().getMethod(model, Object.class);
                insertList.invoke(dao, data);
            }
        } catch (Exception e) {
            log.warn("创建产品出错:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 自行转换
     *
     * @param MethodName 属性参数
     * @return
     */
    private static String getConstructionMethod(String MethodName) {
        StringBuilder sb = new StringBuilder();
        String head = MethodName.substring(0, 1).toUpperCase();
        String tail = MethodName.substring(1);
        sb.append("set").append(head).append(tail);
        return sb.toString();
    }
}
