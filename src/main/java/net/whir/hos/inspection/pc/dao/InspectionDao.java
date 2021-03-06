package net.whir.hos.inspection.pc.dao;

import net.whir.hos.inspection.pc.bean.Inspection;
import net.whir.hos.inspection.pc.bean.RemindUnified;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

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

    /**
     * 根据巡检天数查询巡检计划
     * @param heaven
     * @return
     */
    List<Inspection> selectByHeaven(int heaven);

    /**
     * 根据人员ID查询巡检
     * @param empId
     */
    List<Inspection> selectByEmployeeId(Long empId);
}
