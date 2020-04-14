package net.whir.hos.inspection.pc.service;

import net.whir.hos.inspection.pc.bean.Building;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/14 10:38 上午
 */

public interface BuildService {

    /**
     * 查询全部楼宇
     *
     * @return
     */
    List<Building> findAll();
}
