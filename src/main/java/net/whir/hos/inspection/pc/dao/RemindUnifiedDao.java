package net.whir.hos.inspection.pc.dao;

import net.whir.hos.inspection.pc.bean.RemindUnified;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/15 10:10 上午
 */
public interface RemindUnifiedDao extends Mapper<RemindUnified> {

    /**
     * 分页查询提醒
     *
     * @param obj
     * @return
     */
    List<RemindUnified> selectUnifiedRemindPage(RemindUnified obj);
}
