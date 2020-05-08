package net.whir.hos.inspection.pc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.Files;
import net.whir.hos.inspection.pc.bean.SpecialEvent;
import net.whir.hos.inspection.pc.dao.FileDao;
import net.whir.hos.inspection.pc.dao.SpecialEventDao;
import net.whir.hos.inspection.pc.service.SpecialEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/26 2:53 下午
 */

@Service
@Transactional
public class SpecialEventServiceImpl implements SpecialEventService {

    @Autowired
    private SpecialEventDao specialEventDao;

    @Autowired
    private FileDao fileDao;

    /**
     * 添加特殊事件
     *
     * @param specialEvent
     */
    @Override
    public void insert(SpecialEvent specialEvent) {
        specialEventDao.insert(specialEvent);
    }

    @Override
    public SpecialEvent selectById(Long id) {
        return specialEventDao.selectByPrimaryKey(id);
    }

    /**
     * 删除特殊事件
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<Long> ids) {
        for (Long id : ids) {
            specialEventDao.deleteByPrimaryKey(id);
            Example example = new Example(Files.class);
            example.createCriteria().andEqualTo("specialId", id);
            fileDao.deleteByExample(example);
        }
    }

    /**
     * 分页查看特殊事件
     *
     * @param pageRequest
     * @return
     */
    @Override
    public Page<SpecialEvent> findPage(PageRequest<SpecialEvent> pageRequest) {
        SpecialEvent specialEvent = new SpecialEvent();
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<SpecialEvent> specialEvents = specialEventDao.selectPage(pageRequest.getObj());
        return (Page<SpecialEvent>) specialEvents;
    }


}
