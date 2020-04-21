package net.whir.hos.inspection.pc.dao;

import net.whir.hos.inspection.pc.bean.InspectionHistory;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/20 4:29 下午
 */

public interface InspectionHistoryDao extends Mapper<InspectionHistory> {

    /**
     * 历史巡检分页查询
     *
     * @param obj
     * @return
     */
    List<InspectionHistory> findPage(InspectionHistory obj);
}
