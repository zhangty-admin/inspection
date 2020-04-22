package net.whir.hos.inspection.pc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.Employee;
import net.whir.hos.inspection.pc.bean.InspectionHistory;
import net.whir.hos.inspection.pc.bean.Item;
import net.whir.hos.inspection.pc.bean.ItemEmployee;
import net.whir.hos.inspection.pc.dao.EmployeeDao;
import net.whir.hos.inspection.pc.dao.InspectionHistoryDao;
import net.whir.hos.inspection.pc.dao.ItemDao;
import net.whir.hos.inspection.pc.service.InspectionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/20 4:28 下午
 */

@Service
@Transactional
public class InspectionHistoryServiceImpl implements InspectionHistoryService {

    @Autowired
    private InspectionHistoryDao historyInspectionDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private EmployeeDao employeeDao;

    /**
     * 历史巡检分页查询
     *
     * @param inspectionHistoryPageResult
     * @return
     */
    @Override
    public Page<InspectionHistory> findPage(PageRequest<InspectionHistory> inspectionHistoryPageResult) {
        PageHelper.startPage(inspectionHistoryPageResult.getPageNum(), inspectionHistoryPageResult.getPageSize());
        return (Page<InspectionHistory>) historyInspectionDao.findPage(inspectionHistoryPageResult.getObj());
    }

    /**
     * 历史巡检根据巡检ID分页检查项
     *
     * @param pageRequest
     * @return
     */
    @Override
    public Page<Employee> findItemsById(PageRequest<Long> pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        return (Page<Employee>) employeeDao.selectByInspectionId(pageRequest.getObj());
    }

}
