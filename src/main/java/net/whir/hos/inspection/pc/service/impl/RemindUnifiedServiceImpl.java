package net.whir.hos.inspection.pc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.whir.hos.inspection.app.service.TaskListService;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.RemindUnified;
import net.whir.hos.inspection.pc.bean.RemindUnifiedInspection;
import net.whir.hos.inspection.pc.bean.RemindUnifiedInspectionIds;
import net.whir.hos.inspection.pc.dao.RemindUnifiedDao;
import net.whir.hos.inspection.pc.dao.RemindUnifiedInspectionDao;
import net.whir.hos.inspection.pc.service.RemindUnifiedService;
import net.whir.hos.inspection.commons.utils.MappingTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;


/**
 * @Author: zty
 * @Date: 2020/4/15 10:10 上午
 */

@Service
@Transactional
public class RemindUnifiedServiceImpl implements RemindUnifiedService {

    @Autowired
    private RemindUnifiedDao remindDao;
    @Autowired
    private RemindUnifiedInspectionDao unifiedRemindInspectionDao;
    @Autowired
    private TaskListService taskListService;

    /**
     * 分页查询提醒
     *
     * @param remindPageRequest
     * @return
     */
    @Override
    public Page<RemindUnified> findPage(PageRequest<RemindUnified> remindPageRequest) {
        PageHelper.startPage(remindPageRequest.getPageNum(), remindPageRequest.getPageSize());
        return (Page<RemindUnified>)remindDao.selectUnifiedRemindPage(remindPageRequest.getObj());
    }

    /**
     * 新增统一提醒
     *
     * @param unifiedRemindDepartment
     */
    @Override
    public void addRemindUnified(RemindUnifiedInspectionIds unifiedRemindDepartment) {
        //统一提醒新增
        remindDao.insertSelective(unifiedRemindDepartment.getUnifiedRemind());
        //新增部门和提醒的中间表
        MappingTableUtil.relateMapping(unifiedRemindInspectionDao, new RemindUnifiedInspection(),
                unifiedRemindDepartment.getUnifiedRemind().getId(), unifiedRemindDepartment.getInspectionIds(), "insert");
        //添加统一提醒任务列表
        taskListService.addTaskList(unifiedRemindDepartment);
    }

    /**
     * 删除统一新增提醒
     *
     * @param remindUnifiedId
     */
    @Override
    public void deleteRemindById(Long remindUnifiedId) {
        //删除部门和提醒的中间表
        if (!delete(remindUnifiedId)) return;
        //删除统一提醒
        remindDao.deleteByPrimaryKey(remindUnifiedId);
        //删除统一提醒任务
        taskListService.deleteTaskList(remindUnifiedId);
    }

    /**
     * 修改统一新增提醒
     *
     * @param unifiedRemindDepartment
     */
    @Override
    public void updateRemind(RemindUnifiedInspectionIds unifiedRemindDepartment) {
        //修改部门和提醒的中间表
        if (!delete(unifiedRemindDepartment.getUnifiedRemind().getId())) return;
        MappingTableUtil.relateMapping(unifiedRemindInspectionDao, new RemindUnifiedInspection(),
                unifiedRemindDepartment.getUnifiedRemind().getId(), unifiedRemindDepartment.getInspectionIds(), "insert");

        //修改统一新增
        remindDao.updateByPrimaryKeySelective(unifiedRemindDepartment.getUnifiedRemind());
        //修改定时任务(删除重新添加)
        taskListService.deleteTaskList(unifiedRemindDepartment.getUnifiedRemind().getId());
        //添加新任务
        taskListService.addTaskList(unifiedRemindDepartment);
    }

    /**
     * 删除中间表信息
     *
     * @param remindOmissionId
     * @return
     */
    private boolean delete(Long remindOmissionId) {
        if (StringUtils.isEmpty(remindOmissionId)) {
            return false;
        }
        Example example = new Example(RemindUnifiedInspection.class);
        example.createCriteria().andEqualTo("unifiedRemindId", remindOmissionId);
        unifiedRemindInspectionDao.deleteByExample(example);
        return true;
    }
}
