package net.whir.hos.inspection.pc.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.pc.service.HistoryInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zty
 * @Date: 2020/4/20 4:27 下午
 */

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/historyInspection")
@Api(description = "历史巡检")
public class HistoryInspectionController {

    @Autowired
    private HistoryInspectionService historyInspectionService;

}
