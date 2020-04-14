package net.whir.hos.inspection.pc.service;

import com.github.pagehelper.Page;
import net.whir.hos.inspection.pc.bean.Inspection;
import net.whir.hos.inspection.pc.bean.InspectionItemIds;
import net.whir.hos.inspection.pc.bean.PageRequest;

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
}
