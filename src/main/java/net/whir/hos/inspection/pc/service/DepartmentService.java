package net.whir.hos.inspection.pc.service;

import net.whir.hos.inspection.pc.bean.Department;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/15 9:26 上午
 */
public interface DepartmentService {

    /**
     * 查询全部部门
     *
     * @return
     */
    List<Department> findAll();
}
