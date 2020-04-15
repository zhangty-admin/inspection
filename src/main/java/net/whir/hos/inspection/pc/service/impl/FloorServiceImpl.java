package net.whir.hos.inspection.pc.service.impl;

import net.whir.hos.inspection.pc.bean.Floor;
import net.whir.hos.inspection.pc.dao.FloorDao;
import net.whir.hos.inspection.pc.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/14 11:07 上午
 */

@Service
@Transactional
public class FloorServiceImpl implements FloorService {

    @Autowired
    private FloorDao floorDao;

    /**
     * 查询全部楼层
     *
     * @return
     */
    @Override
    public List<Floor> findFloorByBuild(Integer buildId) {
        Example example = new Example(Floor.class);
        example.createCriteria().andEqualTo("building", buildId);
        return floorDao.selectByExample(example);
    }
}
