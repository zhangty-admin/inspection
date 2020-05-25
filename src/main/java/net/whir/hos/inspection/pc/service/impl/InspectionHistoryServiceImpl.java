package net.whir.hos.inspection.pc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.commons.entity.PageResult;
import net.whir.hos.inspection.pc.bean.*;
import net.whir.hos.inspection.pc.dao.EmployeeDao;
import net.whir.hos.inspection.pc.dao.InspectionDao;
import net.whir.hos.inspection.pc.dao.InspectionHistoryDao;
import net.whir.hos.inspection.pc.dao.ItemDao;
import net.whir.hos.inspection.pc.service.InspectionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
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
    private InspectionDao inspectionDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ItemDao itemDao;

    /**
     * 历史巡检分页查询
     *
     * @param inspectionHistoryPageResult
     * @return
     */
    @Override
    public PageResult findPage(PageRequest<Inspection> inspectionHistoryPageResult) {
        PageHelper.startPage(inspectionHistoryPageResult.getPageNum(), inspectionHistoryPageResult.getPageSize());
        Page<InspectionHistory> page = (Page<InspectionHistory>) historyInspectionDao.findPage(inspectionHistoryPageResult.getMap());
        List<InspectionHistory> result = page.getResult();

        for (InspectionHistory inspectionHistory : result) {
            Inspection obj = inspectionHistoryPageResult.getObj();
            if (StringUtils.isEmpty(obj)) {
                obj = new Inspection();
                obj.setId(inspectionHistory.getInspectionId());
            }
            obj.setId(inspectionHistory.getInspectionId());
            List<Inspection> inspections = inspectionDao.selectInspectionPage(obj);
            inspectionHistory.setInspection(inspections);
        }

        List<Inspection> inspections = new ArrayList<>();
        List<InspectionHistory> inspectionHistories = new ArrayList<>();
        for (InspectionHistory inspectionHistory : result) {
            for (Inspection inspection : inspectionHistory.getInspection()) {
                if (!StringUtils.isEmpty(inspection)) {
                    inspections.add(inspection);
                    inspectionHistories.add(inspectionHistory);
                }
            }
        }

        return new PageResult(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), inspectionHistories);
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

    /**
     * 历史巡检根据巡检ID和人员ID检查项
     *
     * @param empId
     * @param insId
     * @return
     */
    @Override
    public List<Item> findItemsByEmpIdAndItemId(Long empId, Long insId) {
        return itemDao.selectItemsByEmpIdAndItemId(empId, insId);
    }

    /**
     * 根据巡检ID和人员ID检查项删除历史巡检
     *
     * @param employeeInspectionIds
     */
    @Override
    public void deleteItemsByEmpIdAndItemId(List<EmployeeIdInspectionId> employeeInspectionIds) {
        Example example = new Example(InspectionHistory.class);
        if (!StringUtils.isEmpty(employeeInspectionIds)) {
            for (EmployeeIdInspectionId employeeInspectionId : employeeInspectionIds) {
                example.createCriteria().andEqualTo("inspectionId", employeeInspectionId.getInspectionId())
                        .andEqualTo("employeeId", employeeInspectionId.getEmployeeId());
                historyInspectionDao.deleteByExample(example);
            }
        }
    }

    /**
     * 计算当天有无操作
     *
     * @param date
     * @return
     */
    @Override
    public InspectionHistory findByCreateTime(String date, Long id) {
        return historyInspectionDao.findByCreateTime(date,id);
    }

    /**
     * 新增历史巡检信息
     *
     * @param inspectionHistory
     */
    @Override
    public void saveInspectionHistory(InspectionHistory inspectionHistory) {
        historyInspectionDao.insertSelective(inspectionHistory);
    }

}
