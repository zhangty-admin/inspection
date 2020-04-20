package net.whir.hos.inspection.pc.service;

import com.github.pagehelper.Page;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.RemindAdmin;


/**
 * @Author: zty
 * @Date: 2020/4/17 1:46 下午
 */
public interface AdminRemindService {

    /**
     * 分页查询管理员提醒
     *
     * @param remindPageRequest
     * @return
     */
    Page<RemindAdmin> findPage(PageRequest<RemindAdmin> remindPageRequest);

    /**
     * 新增提醒管理员
     *
     * @param adminRemind
     */
    void add(RemindAdmin adminRemind);
}
