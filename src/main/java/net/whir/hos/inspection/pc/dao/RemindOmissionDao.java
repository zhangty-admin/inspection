package net.whir.hos.inspection.pc.dao;

import net.whir.hos.inspection.pc.bean.RemindOmission;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/17 3:34 下午
 */

public interface RemindOmissionDao extends Mapper<RemindOmission> {

    /**
     * 分页查询漏检提醒
     *
     * @param obj
     * @return
     */
    List<RemindOmission> findPageRemindOmission(RemindOmission obj);
}
