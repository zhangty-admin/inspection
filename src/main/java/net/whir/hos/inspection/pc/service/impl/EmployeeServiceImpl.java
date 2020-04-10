package net.whir.hos.inspection.pc.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.pc.bean.Employee;
import net.whir.hos.inspection.pc.dao.DepartmentDao;
import net.whir.hos.inspection.pc.dao.EmployeeDao;
import net.whir.hos.inspection.pc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @Author: zty
 * @Date: 2020/4/7 10:14 上午
 */
@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DepartmentDao departmentDao;

    /**
     * 分页条件查询用户信息
     *
     * @param map
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Employee> findPage(Map<String, Object> map, int page, int size) {
        PageHelper.startPage(page, size);
        Employee employee = JSON.parseObject(JSON.toJSONString(map), Employee.class);
        List<Employee> employees = employeeDao.findPage(employee);
        return (Page<Employee>) employees;
    }

    /**
     * 删除用户
     *
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        employeeDao.deleteByPrimaryKey(id);
    }

    /**
     * 修改用户信息/审核用户信息
     *
     * @param employee
     */
    @Override
    public void updateByEmployee(Employee employee) {
        employeeDao.updateByPrimaryKeySelective(employee);
    }

    //分页查询条件
    public Example createExample(Map<String, Object> map) {
        Employee employee = JSON.parseObject(JSON.toJSONString(map), Employee.class);
        Example example = new Example(Employee.class);
        Example.Criteria criteria = example.createCriteria();
        if (employee == null) {
            return example;
        }

        //id
        if (employee.getId() != null && !"".equals(employee.getId())) {
            criteria.andEqualTo("id", employee.getId());
        }

        //name
        if (employee.getName() != null && !"".equals(employee.getName())) {
            criteria.andLike("name", "%" + employee.getName() + "%");
        }

        //createTime
        if (employee.getCreateTime() != null && !"".equals(employee.getCreateTime())) {
            criteria.andEqualTo("create_time", employee.getCreateTime());
        }

        //sex
        if (employee.getSex() != null && !"".equals(employee.getSex())) {
            criteria.andEqualTo("sex", employee.getSex());
        }

        //phone
        if (employee.getPhone() != null && !"".equals(employee.getPhone())) {
            criteria.andEqualTo("phone", employee.getPhone());
        }

        //review
        if (employee.getReview() != null && !"".equals(employee.getReview())) {
            criteria.andEqualTo("review", employee.getReview());
        }
        return example;
    }
}