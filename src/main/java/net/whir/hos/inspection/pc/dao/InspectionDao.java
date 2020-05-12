package net.whir.hos.inspection.pc.dao;

import net.whir.hos.inspection.pc.bean.Inspection;
import net.whir.hos.inspection.pc.bean.RemindUnified;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/9 4:53 下午
 */
public interface InspectionDao extends Mapper<Inspection> {

    /**
     * 分页查询巡检计划
     *
     * @param obj
     * @return
     */
    List<Inspection> selectInspectionPage(Inspection obj);

    /**
     * 根据统一提醒查询巡检计划
     *
     * @param id
     * @return
     */
    List<Inspection> findByUnifiedId(Long id);
}
