package net.whir.hos.inspection.pc.dao;

import net.whir.hos.inspection.pc.bean.Employee;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * @Author: zty
 * @Date: 2020/4/7 10:14 上午
 */
public interface EmployeeDao extends Mapper<Employee> {

    /**
     * 分页条件查询用户信息
     *
     * @param employee
     * @return
     */
    List<Employee> findPage(Employee employee);

    /**
     * 查询历史巡检计划
     *
     * @param obj
     * @return
     */
    List<Employee> selectByInspectionId(Long obj);

    /**
     * 根据部门ID查询出当前所有人
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
     * 根据管理人查询用户信息
     * @return
     */
    List<Employee> selectAllByRemindAdmin();

    /**
     * 获取管理员信息
     * @return
     */
    List<Employee> selectAllByUser();
}
