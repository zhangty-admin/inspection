package net.whir.hos.inspection.pc.dao;

import net.whir.hos.inspection.pc.bean.SpecialEvent;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/26 2:53 下午
 */

public interface SpecialEventDao extends Mapper<SpecialEvent> {

    /**
     * 分页查看特殊事件
     *
     * @param obj
     * @return
     */
    List<SpecialEvent> selectPage(SpecialEvent obj);
}
