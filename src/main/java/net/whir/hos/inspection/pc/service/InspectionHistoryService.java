package net.whir.hos.inspection.pc.service;

import com.github.pagehelper.Page;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.Employee;
import net.whir.hos.inspection.pc.bean.InspectionHistory;
import net.whir.hos.inspection.pc.bean.ItemEmployee;

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
    Page<InspectionHistory> findPage(PageRequest<InspectionHistory> inspectionHistoryPageResult);

    /**
     * 历史巡检根据巡检ID分页检查项
     *
     * @param pageRequest
     * @return
     */
    Page<Employee> findItemsById(PageRequest<Long> pageRequest);
}
