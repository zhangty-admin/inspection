package net.whir.hos.inspection.pc.service.impl;

import net.whir.hos.inspection.pc.dao.HistoryInspectionDao;
import net.whir.hos.inspection.pc.service.HistoryInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zty
 * @Date: 2020/4/20 4:28 下午
 */

@Service
public class HistoryInspectionServiceImpl implements HistoryInspectionService {

    @Autowired
    private HistoryInspectionDao historyInspectionDao;

}
