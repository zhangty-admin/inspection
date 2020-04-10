package net.whir.hos.inspection.pc.service;

import net.whir.hos.inspection.pc.bean.Inspection;
import net.whir.hos.inspection.pc.bean.InspectionItemIds;

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


}