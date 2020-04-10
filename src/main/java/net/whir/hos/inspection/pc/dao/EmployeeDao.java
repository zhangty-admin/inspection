package net.whir.hos.inspection.pc.dao;

import net.whir.hos.inspection.pc.bean.Employee;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * @Author: zty
 * @Date: 2020/4/7 10:14 上午
 */
public interface EmployeeDao extends Mapper<Employee> {

    List<Employee> findPage(Employee employee);

}
