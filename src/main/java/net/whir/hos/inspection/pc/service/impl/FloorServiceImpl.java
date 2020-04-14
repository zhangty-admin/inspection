package net.whir.hos.inspection.pc.service.impl;

import net.whir.hos.inspection.pc.bean.Floor;
import net.whir.hos.inspection.pc.dao.FloorDao;
import net.whir.hos.inspection.pc.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/14 11:07 上午
 */

@Service
public class FloorServiceImpl implements FloorService {

    @Autowired
    private FloorDao floorDao;

    /**
     * 查询全部楼层
     *
     * @return
     */
    @Override
    public List<Floor> findFloorAll() {
        return floorDao.selectAll();
    }
}
