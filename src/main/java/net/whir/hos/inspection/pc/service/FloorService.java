package net.whir.hos.inspection.pc.service;

import net.whir.hos.inspection.pc.bean.Floor;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/14 11:06 上午
 */

public interface FloorService {

    /**
     * 查询全部楼层
     *
     * @return
     */
    List<Floor> findFloorAll();
}
