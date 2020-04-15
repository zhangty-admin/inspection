package net.whir.hos.inspection.pc.service;

import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.Remind;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/15 10:09 上午
 */
public interface RemindService {

    /**
     * 分页查询提醒
     *
     * @param remindPageRequest
     * @return
     */
    List<Remind> findPage(PageRequest<Remind> remindPageRequest);
}
