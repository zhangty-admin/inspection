package net.whir.hos.inspection.pc.service;

import com.github.pagehelper.Page;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.commons.entity.PageResult;
import net.whir.hos.inspection.pc.bean.*;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/20 4:28 下午
 */

public interface InspectionHistoryService {

    /**
     * 历史巡检分页查询
     *
     * @param inspectionHistoryPageResult
     * @return
     */
    PageResult findPage(PageRequest<Inspection> inspectionHistoryPageResult);

    /**
     * 历史巡检根据巡检ID分页检查项
     *
     * @param pageRequest
     * @return
     */
    Page<Employee> findItemsById(PageRequest<Long> pageRequest);

    /**
     * 历史巡检根据巡检ID和人员ID检查项
     *
     * @param empId
     * @param insId
     * @return
     */
    List<Item> findItemsByEmpIdAndItemId(Long empId, Long insId);

    /**
     * 根据巡检ID和人员ID检查项删除历史巡检
     *
     * @param employeeInspectionIds
     */
    void deleteItemsByEmpIdAndItemId(List<EmployeeInspectionId> employeeInspectionIds);

    /**
     * 计算当天有无操作
     * @param date
     * @return
     */
    InspectionHistory findByCreateTime(String date, Long id);

    /**
     * 新增历史巡检信息
     * @param inspectionHistory
     */
    void saveInspectionHistory(InspectionHistory inspectionHistory);
}
