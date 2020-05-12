package net.whir.hos.inspection.pc.service;

import com.github.pagehelper.Page;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.SpecialEvent;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/26 2:53 下午
 */

public interface SpecialEventService {

    /**
     * 添加特殊事件
     *
     * @param specialEvent
     */
    void insertSpecialEvent(SpecialEvent specialEvent);

    SpecialEvent selectById(Long id);

    /**
     * 删除特殊事件
     *
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 分页查看特殊事件
     *
     * @param pageRequest
     * @return
     */
    Page<SpecialEvent> findPage(PageRequest<SpecialEvent> pageRequest);

    /**
     * 根据ID查询特殊事件
     *
     * @param id
     * @return
     */
    SpecialEvent findSpecialEventById(Long id);
}
