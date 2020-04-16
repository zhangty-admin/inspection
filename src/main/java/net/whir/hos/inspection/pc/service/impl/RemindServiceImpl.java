package net.whir.hos.inspection.pc.service.impl;

import com.github.pagehelper.PageHelper;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.UnifiedRemind;
import net.whir.hos.inspection.pc.bean.UnifiedRemindDepartment;
import net.whir.hos.inspection.pc.dao.RemindDao;
import net.whir.hos.inspection.pc.service.RemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/15 10:10 上午
 */

@Service
@Transactional
public class RemindServiceImpl implements RemindService {

    @Autowired
    private RemindDao remindDao;

    /**
     * 分页查询提醒
     *
     * @param remindPageRequest
     * @return
     */
    @Override
    public List<UnifiedRemind> findPage(PageRequest<UnifiedRemind> remindPageRequest) {
        PageHelper.startPage(remindPageRequest.getPageNum(), remindPageRequest.getPageSize());
        return remindDao.selectRemindPage(remindPageRequest.getObj());
    }

    /**
     * 新增统一提醒
     *
     * @param unifiedRemindDepartment
     */
    @Override
    public void add(UnifiedRemindDepartment unifiedRemindDepartment) {
        //修改部门

        //统一提醒新增
        remindDao.insertSelective(unifiedRemindDepartment.getUnifiedRemind());
    }

    /**
     * 删除统一新增提醒
     *
     * @param unifiedRemindDepartment
     */
    @Override
    public void deleteRemindById(UnifiedRemindDepartment unifiedRemindDepartment) {
        //删除部门

        //删除统一提醒
        remindDao.deleteByPrimaryKey(unifiedRemindDepartment.getUnifiedRemind().getId());
    }

    /**
     * 修改统一新增提醒
     *
     * @param unifiedRemindDepartment
     */
    @Override
    public void updateRemind(UnifiedRemindDepartment unifiedRemindDepartment) {
        //修改部门
        //修改统一新增
        remindDao.updateByPrimaryKeySelective(unifiedRemindDepartment.getUnifiedRemind());
    }
}
