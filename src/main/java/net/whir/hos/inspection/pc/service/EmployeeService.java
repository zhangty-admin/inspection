package net.whir.hos.inspection.pc.service;

import com.github.pagehelper.Page;
import net.whir.hos.inspection.pc.bean.Employee;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/7 10:14 上午
 */

public interface EmployeeService {

    /**
     * 分页条件查询用户信息
     *
     * @param obj
     * @param page
     * @param size
     * @return
     */
    Page<Employee> findPage(Employee obj, int page, int size);

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

    /**
     * 查询全部用户
     *
     * @return
     */
    List<Employee> findAll(Employee employee);

    /**
     * 新增用户信息
     *
     * @param employee
     */
    void insertEmployee(Employee employee);

    /**
     * 根据统一查询人员信息
     *
     * @param id
     * @return
     */
    List<Employee> selectByUnifiedRemindId(Long id);

    /**
     * 根据漏检查询人员信息
     *
     * @param id
     * @return
     */
    List<Employee> selectByOmissionId(Long id);

    /**
     * 审批通知管理员
     *
     * @param review
     */
    void sendAdminApprove(Boolean review, Long empId);
}
