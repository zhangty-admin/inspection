package net.whir.hos.inspection.pc.service.impl;

import net.whir.hos.inspection.pc.bean.Department;
import net.whir.hos.inspection.pc.dao.DepartmentDao;
import net.whir.hos.inspection.pc.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/15 9:26 上午
 */

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public List<Department> findAll() {
        return departmentDao.selectAll();
    }
}
