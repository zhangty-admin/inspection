package net.whir.hos.inspection.pc.service.impl;

import net.whir.hos.inspection.pc.bean.Building;
import net.whir.hos.inspection.pc.dao.BuildDao;
import net.whir.hos.inspection.pc.service.BuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/14 10:38 上午
 */

@Service
@Transactional
public class BuildServiceImpl implements BuildService {

    @Autowired
    private BuildDao buildDao;

    /**
     * 查询全部楼宇
     *
     * @return
     */
    @Override
    public List<Building> findAll() {
        return buildDao.selectAll();
    }
}
