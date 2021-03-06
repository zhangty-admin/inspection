package net.whir.hos.inspection.pc.dao;

import net.whir.hos.inspection.pc.bean.InspectionHistory;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: zty
 * @Date: 2020/4/20 4:29 下午
 */

public interface InspectionHistoryDao extends Mapper<InspectionHistory> {

    /**
     * 历史巡检分页查询
     *
     * @param map
     * @return
     */
    List<InspectionHistory> findPage(Map<String, Object> map);

    /**
     * 计算当天有无操作
     *
     * @param date
     * @return
     */
    InspectionHistory findByCreateTime(String date,Long id);
}
