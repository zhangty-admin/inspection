package net.whir.hos.inspection.pc.service.impl;

import com.github.pagehelper.PageHelper;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.AdminRemind;
import net.whir.hos.inspection.pc.dao.AdminRemindDao;
import net.whir.hos.inspection.pc.service.AdminRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/17 1:47 下午
 */

@Service
public class AdminReminServiceImpl implements AdminRemindService {

    @Autowired
    private AdminRemindDao adminRemindDao;

    /**
     * 分页查询管理员提醒
     *
     * @param remindPageRequest
     * @return
     */
    @Override
    public List<AdminRemind> findPage(PageRequest<AdminRemind> remindPageRequest) {
        PageHelper.startPage(remindPageRequest.getPageNum(), remindPageRequest.getPageSize());
        return adminRemindDao.findPageAdminRemind(remindPageRequest.getObj());
    }

    /**
     * 新增提醒管理员
     *
     * @param adminRemind
     */
    @Override
    public void add(AdminRemind adminRemind) {
        adminRemindDao.insert(adminRemind);
    }
}
