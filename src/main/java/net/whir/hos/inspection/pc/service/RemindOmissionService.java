package net.whir.hos.inspection.pc.service;

import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.RemindOmission;
import net.whir.hos.inspection.pc.bean.RemindOmissionDepartmentIds;
import net.whir.hos.inspection.pc.bean.RemindUnifiedDepartmentIds;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/17 3:33 下午
 */
public interface RemindOmissionService {

    /**
     * 分页查询漏检提醒
     *
     * @param remindPageRequest
     * @return
     */
    List<RemindOmission> findPage(PageRequest<RemindOmission> remindPageRequest);

    /**
     * 新增漏检提醒
     *
     * @param remindOmissionDepartmentIds
     */
    void add(RemindOmissionDepartmentIds remindOmissionDepartmentIds);

    /**
     * 删除漏检新增提醒
     *
     * @param remindOmissionId
     */
    void deleteRemindById(Integer remindOmissionId);

    /**
     * 修改漏检新增提醒
     *
     * @param remindOmissionDepartmentIds
     */
    void updateRemind(RemindOmissionDepartmentIds remindOmissionDepartmentIds);
}
