package net.whir.hos.inspection.pc.dao;

import net.whir.hos.inspection.pc.bean.UnifiedRemind;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/15 10:10 上午
 */
public interface UnifiedRemindDao extends Mapper<UnifiedRemind> {

    /**
     * 分页查询提醒
     *
     * @param obj
     * @return
     */
    List<UnifiedRemind> selectUnifiedRemindPage(UnifiedRemind obj);
}
