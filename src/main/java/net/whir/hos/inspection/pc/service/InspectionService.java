package net.whir.hos.inspection.pc.service;

import com.github.pagehelper.Page;
import net.whir.hos.inspection.pc.bean.Inspection;
import net.whir.hos.inspection.pc.bean.InspectionItemIds;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.RemindUnified;

import java.util.List;


/**
 * @Author: zty
 * @Date: 2020/4/9 4:51 下午
 */
public interface InspectionService {

    /**
     * 新增巡检计划
     *
     * @param inspectionItemIds
     */
    void add(InspectionItemIds inspectionItemIds);


    /**
     * 修改巡检计划
     *
     * @param inspectionItemIds
     */
    void update(InspectionItemIds inspectionItemIds);

    /**
     * 删除巡检计划
     *
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 分页查询巡检计划
     *
     * @param pageRequest
     */
    Page<Inspection> findPage(PageRequest<Inspection> pageRequest);

    /**
     * 查询全部巡检计划
     *
     * @return
     */
    List<Inspection> findAll();

    /**
     * 根据统一提醒查询巡检计划
     *
     * @param ids
     * @return
     */
    List<Inspection> findById(List<Long> ids);
}
