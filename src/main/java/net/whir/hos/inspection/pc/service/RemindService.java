package net.whir.hos.inspection.pc.service;

import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.UnifiedRemind;
import net.whir.hos.inspection.pc.bean.UnifiedRemindDepartmentIds;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/15 10:09 上午
 */
public interface RemindService {

    /**
     * 分页查询提醒
     *
     * @param remindPageRequest
     * @return
     */
    List<UnifiedRemind> findPage(PageRequest<UnifiedRemind> remindPageRequest);

    /**
     * 新增统一提醒
     *
     * @param unifiedRemindDepartment
     */
    void add(UnifiedRemindDepartmentIds unifiedRemindDepartment);

    /**
     * 删除统一新增提醒
     *
     * @param unifiedRemindDepartment
     */
    void deleteRemindById(UnifiedRemindDepartmentIds unifiedRemindDepartment);

    /**
     * 修改统一新增提醒
     *
     * @param unifiedRemindDepartment
     */
    void updateRemind(UnifiedRemindDepartmentIds unifiedRemindDepartment);
}
