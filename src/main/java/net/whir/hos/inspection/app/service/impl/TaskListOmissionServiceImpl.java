package net.whir.hos.inspection.app.service.impl;

import net.whir.hos.inspection.app.bean.TaskList;
import net.whir.hos.inspection.app.bean.TaskListOmission;
import net.whir.hos.inspection.app.config.MyQuartzScheduler;
import net.whir.hos.inspection.app.dao.TaskListDao;
import net.whir.hos.inspection.app.dao.TaskListOmissionDao;
import net.whir.hos.inspection.app.service.TaskListOmissionService;
import net.whir.hos.inspection.app.service.TaskListService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/5/6 11:09 上午
 */

@Service
@Transactional
public class TaskListOmissionServiceImpl implements TaskListOmissionService {

    @Autowired
    private TaskListOmissionDao taskListOmissionDao;

    /**
     * 新增漏检巡检消息
     *
     * @param taskList
     * @param quartzScheduler
     */
    @Override
    public void addRemindUnified(TaskListOmission taskList, MyQuartzScheduler quartzScheduler) {
        //保存
        taskListOmissionDao.insertSelective(taskList);
        //执行
        try {
            quartzScheduler.addOmissionJob(taskList);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询全部状态为1的漏检提醒
     *
     * @return
     */
    @Override
    public List<TaskListOmission> findByStatus() {
        Example example = new Example(TaskListOmission.class);
        example.createCriteria().andEqualTo("status", 1);
        return taskListOmissionDao.selectByExample(example);
    }

    /**
     * 修改状态
     *
     * @param taskList
     */
    @Override
    public void update(TaskListOmission taskList) {
        taskListOmissionDao.updateByPrimaryKeySelective(taskList);
    }
}
