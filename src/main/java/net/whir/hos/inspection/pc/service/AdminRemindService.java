package net.whir.hos.inspection.pc.service;

import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.RemindAdmin;

import java.util.List;

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
    List<RemindAdmin> findPage(PageRequest<RemindAdmin> remindPageRequest);

    /**
     * 新增提醒管理员
     *
     * @param adminRemind
     */
    void add(RemindAdmin adminRemind);
}
