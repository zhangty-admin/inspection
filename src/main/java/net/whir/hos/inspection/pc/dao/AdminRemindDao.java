package net.whir.hos.inspection.pc.dao;

import net.whir.hos.inspection.pc.bean.AdminRemind;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/17 1:48 下午
 */
public interface AdminRemindDao extends Mapper<AdminRemind> {

    /**
     * 分页查询管理员提醒
     *
     * @param obj
     * @return
     */
    List<AdminRemind> findPageAdminRemind(AdminRemind obj);
}
