package net.whir.hos.inspection.pc.dao;

import net.whir.hos.inspection.pc.bean.Remind;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/15 10:10 上午
 */
public interface RemindDao extends Mapper<Remind> {

    /**
     * 分页查询提醒
     *
     * @param obj
     * @return
     */
    List<Remind> selectRemindPage(Remind obj);
}
