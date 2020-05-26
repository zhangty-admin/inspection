package net.whir.hos.inspection.pc.controller;

import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.commons.entity.PageResult;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.pc.bean.Inspection;
import net.whir.hos.inspection.pc.bean.InspectionHistory;
import net.whir.hos.inspection.pc.bean.RemindOmission;
import net.whir.hos.inspection.pc.bean.RemindOmissionInspectionIds;
import net.whir.hos.inspection.pc.dao.InspectionDao;
import net.whir.hos.inspection.pc.dao.InspectionHistoryDao;
import net.whir.hos.inspection.pc.service.RemindOmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @Author: zty
 * @Date: 2020/4/17 3:01 下午
 */

@RestController
@CrossOrigin
@Slf4j
@Api(description = "漏检消息")
@RequestMapping("/omission")
public class RemindOmissionController {

    @Autowired
    private RemindOmissionService remindOmissionService;
    @Autowired
    private InspectionHistoryDao inspectionHistoryDao;
    @Autowired
    private InspectionDao inspectionDao;

    @ApiOperation(value = "分页查询漏检提醒")
    @PostMapping("/findPage")
    private Result findPageRemindOmission(@RequestBody PageRequest<RemindOmission> remindPageRequest) {
        Page<RemindOmission> page = remindOmissionService.findPage(remindPageRequest);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<RemindOmission>(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), page.getResult()));
    }

    @ApiOperation(value = "新增漏检提醒")
    @PostMapping("/add")
    private Result addRemind(@RequestBody RemindOmissionInspectionIds remindOmissionDepartmentIds) {
        try {
            remindOmissionService.addRemind(remindOmissionDepartmentIds);
        } catch (Exception e) {
            log.warn("添加失败: " + e.getMessage());
            return new Result(true, StatusCode.ERROR, "添加失败");
        }
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @ApiOperation(value = "删除漏检新增提醒")
    @DeleteMapping("/delete/{remindOmissionId}")
    private Result deleteRemind(@PathVariable Integer remindOmissionId) {
        try {
            remindOmissionService.deleteRemindById(remindOmissionId);
        } catch (Exception e) {
            log.warn("删除失败: " + e.getMessage());
            return new Result(true, StatusCode.ERROR, "删除失败");
        }
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @ApiOperation(value = "修改漏检新增提醒")
    @PutMapping("/update")
    private Result updateRemind(@RequestBody RemindOmissionInspectionIds remindOmissionDepartmentIds) {
        try {
            remindOmissionService.updateRemind(remindOmissionDepartmentIds);
        } catch (Exception e) {
            log.warn("修改失败: " + e.getMessage());
            return new Result(true, StatusCode.ERROR, "修改失败");
        }
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @ApiOperation(value = "昨日漏检提醒")
    @GetMapping("/yesterdayMissed")
    private Result yesterdayMissed() {
        //获取昨日日期
        String date = getDate(false);
        //获取工作日日期
        String date5 = getDate(true);
        //获取昨天7天的漏检的信息
        List<Inspection> inspectionOmissions7 = getInspectionOmissions(date, 7);
        //获取昨天5天的漏检的信息
        List<Inspection> inspectionOmissions5 = getInspectionOmissions(date5, 5);
        //获取昨天30天的漏检的信息
        List<Inspection> inspectionOmissions1 = getInspectionOmissions(date, 1);

        List<Inspection> inspectionList = new ArrayList<>();
        inspectionList.addAll(inspectionOmissions1);
        inspectionList.addAll(inspectionOmissions5);
        inspectionList.addAll(inspectionOmissions7);

        return new Result(true, StatusCode.OK, "查询成功", inspectionList);
    }


    /**
     * 获取日期
     *
     * @param bool 是否需要检查为周一
     * @return
     */
    private String getDate(Boolean bool) {
        String date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        //判断是不是周一
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        if (bool) {
            if (week == 1) {
                //获取上周五的日期
                int day = cal.get(Calendar.DATE) - 2;
                date = year + "-" + month + "-" + day;
                return date;
            }
        }
        //获取当天的日期
        int day = cal.get(Calendar.DATE) - 1;
        date = year + "-" + month + "-" + day;
        return date;
    }

    /**
     * 返回昨日漏检计划
     *
     * @param date
     * @param heaven
     * @return
     */
    private List<Inspection> getInspectionOmissions(String date, int heaven) {
        //获取工作日检查的巡检
        List<Inspection> inspections = inspectionDao.selectByHeaven(heaven);

        List<Inspection> inspectionList = new ArrayList<>();
        //判断每个巡检任务有无漏检
        for (Inspection inspection : inspections) {
            Example inspectionHistoryExample = new Example(InspectionHistory.class);
            inspectionHistoryExample.createCriteria().andEqualTo("createTime", date)
                    .andEqualTo("frequency", inspection.getFrequency()).andEqualTo("inspectionId", inspection.getId());
            List<InspectionHistory> inspectionHistories = inspectionHistoryDao.selectByExample(inspectionHistoryExample);
            //不为空说明巡检计划频率对了
            if (StringUtils.isEmpty(inspectionHistories) || inspectionHistories.size() == 0) {
                //为空说明漏检了
                inspectionList.add(inspection);
            }
        }
        return inspectionList;
    }

}
