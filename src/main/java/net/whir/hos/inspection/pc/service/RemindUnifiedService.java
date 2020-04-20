package net.whir.hos.inspection.pc.service;

import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.RemindUnified;
import net.whir.hos.inspection.pc.bean.RemindUnifiedDepartmentIds;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/15 10:09 上午
 */
public interface RemindUnifiedService {

    /**
     * 分页查询提醒
     *
     * @param remindPageRequest
     * @return
     */
    List<RemindUnified> findPage(PageRequest<RemindUnified> remindPageRequest);

    /**
     * 新增统一提醒
     *
     * @param unifiedRemindDepartment
     */
    void add(RemindUnifiedDepartmentIds unifiedRemindDepartment);

    /**
     * 删除统一新增提醒
     *
     * @param unifiedRemindDepartment
     */
    void deleteRemindById(RemindUnifiedDepartmentIds unifiedRemindDepartment);

    /**
     * 修改统一新增提醒
     *
     * @param unifiedRemindDepartment
     */
    void updateRemind(RemindUnifiedDepartmentIds unifiedRemindDepartment);
}