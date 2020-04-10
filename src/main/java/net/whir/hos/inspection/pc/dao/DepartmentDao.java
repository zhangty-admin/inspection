package net.whir.hos.inspection.pc.dao;

import net.whir.hos.inspection.pc.bean.Department;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author: zty
 * @Date: 2020/4/8 2:31 下午
 */
public interface DepartmentDao extends Mapper<Department> {

    Department queryDepartment(Long id);
}
