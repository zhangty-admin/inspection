package net.whir.hos.inspection.pc.service.impl;

import com.github.pagehelper.PageHelper;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.Remind;
import net.whir.hos.inspection.pc.dao.RemindDao;
import net.whir.hos.inspection.pc.service.RemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/15 10:10 上午
 */

@Service
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
    public List<Remind> findPage(PageRequest<Remind> remindPageRequest) {
        PageHelper.startPage(remindPageRequest.getPageNum(), remindPageRequest.getPageSize());
        return remindDao.selectRemindPage(remindPageRequest.getObj());
    }
}
