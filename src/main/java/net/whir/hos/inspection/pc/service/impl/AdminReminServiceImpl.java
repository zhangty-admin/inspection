package net.whir.hos.inspection.pc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.Page;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.RemindAdmin;
import net.whir.hos.inspection.pc.dao.RemindAdminDao;
import net.whir.hos.inspection.pc.service.AdminRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author: zty
 * @Date: 2020/4/17 1:47 下午
 */

@Service
@Transactional
public class AdminReminServiceImpl implements AdminRemindService {

    @Autowired
    private RemindAdminDao adminRemindDao;

    /**
     * 分页查询管理员提醒
     *
     * @param remindPageRequest
     * @return
     */
    @Override
    public Page<RemindAdmin> findPage(PageRequest<RemindAdmin> remindPageRequest) {
        PageHelper.startPage(remindPageRequest.getPageNum(), remindPageRequest.getPageSize());
        return (Page<RemindAdmin>)adminRemindDao.findPageAdminRemind(remindPageRequest.getObj());
    }

    /**
     * 新增提醒管理员
     *
     * @param adminRemind
     */
    @Override
    public void add(RemindAdmin adminRemind) {
        adminRemindDao.insert(adminRemind);
    }
}
