package net.whir.hos.inspection.app.service.impl;

import net.whir.hos.inspection.app.bean.TaskList;
import net.whir.hos.inspection.app.config.MyQuartzScheduler;
import net.whir.hos.inspection.app.dao.TaskListDao;
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
public class TaskListServiceImpl implements TaskListService {

    @Autowired
    private TaskListDao taskListDao;

    /**
     * 新增统一巡检消息
     *
     * @param taskList
     * @param quartzScheduler
     */
    @Override
    public void addRemindUnified(TaskList taskList, MyQuartzScheduler quartzScheduler) {
        //保存
        taskListDao.insertSelective(taskList);
        //执行
        try {
            quartzScheduler.addJob(taskList);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询全部状态为1的统一提醒
     *
     * @return
     */
    @Override
    public List<TaskList> findByStatus() {
        Example example = new Example(TaskList.class);
        example.createCriteria().andEqualTo("status", 1);
        return taskListDao.selectByExample(example);
    }

    /**
     * 修改状态
     *
     * @param taskList
     */
    @Override
    public void update(TaskList taskList) {
        taskListDao.updateByPrimaryKeySelective(taskList);
    }
}
