package net.whir.hos.inspection.pc.service;

import com.github.pagehelper.Page;
import net.whir.hos.inspection.pc.bean.Employee;

import java.util.Map;

/**
 * @Author: zty
 * @Date: 2020/4/7 10:14 上午
 */
public interface EmployeeService {

    /**
     * 分页条件查询用户信息
     *
     * @param map
     * @param page
     * @param size
     * @return
     */
    Page<Employee> findPage(Map<String, Object> map, int page, int size);

    /**
     * 删除用户
     *
     * @param id
     */
    void deleteById(Long id);

    /**
     * 修改用户信息/审核用户信息
     *
     * @param employee
     */
    void updateByEmployee(Employee employee);
}
