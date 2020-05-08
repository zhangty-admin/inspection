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
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @Author: zty
 * @Date: 2020/4/7 10:14 上午
 */
@Service
@Slf4j
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DepartmentDao departmentDao;

    /**
     * 分页条件查询用户信息
     *
     * @param obj
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Employee> findPage(Employee obj, int page, int size) {
        PageHelper.startPage(page, size);
        List<Employee> employees = employeeDao.findPage(obj);
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

    /**
     * 查询全部用户
     *
     * @return
     */
    @Override
    public List<Employee> findAll(Employee employee) {
        Example example = createExample(employee);
        return employeeDao.selectByExample(example);
    }

    /**
     * 新增用户信息
     *
     * @param employee
     */
    @Override
    public void insert(Employee employee) {
        employee.setReview(0);
        employeeDao.insert(employee);
    }

    @Override
    public List<Employee> selectByunifiedremindId(Long id) {
         return employeeDao.selectByDepartment(id);
    }

    //分页查询条件
    public Example createExample(Employee employee) {
//        Employee employee = JSON.parseObject(JSON.toJSONString(map), Employee.class);
        Example example = new Example(Employee.class);
        example.setOrderByClause("ID DESC");
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
            criteria.andEqualTo("createTime", employee.getCreateTime());
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

        //review
        if (employee.getIsPrincipal() != null && !"".equals(employee.getIsPrincipal())) {
            criteria.andEqualTo("isPrincipal", employee.getIsPrincipal());
        }

        return example;
    }
}
