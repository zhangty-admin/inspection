package net.whir.hos.inspection.pc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.commons.utils.MultiRequest;
import net.whir.hos.inspection.commons.utils.WXToken;
import net.whir.hos.inspection.pc.bean.Employee;
import net.whir.hos.inspection.pc.dao.EmployeeDao;
import net.whir.hos.inspection.pc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
     * @param emp
     */
    @Override
    public void insertEmployee(Employee emp) {
        emp.setReview(0);
        employeeDao.insert(emp);

        //查询人员 发送消息 拼接模版
        List<Employee> employees = employeeDao.selectAllByRemindAdmin();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        for (Employee employee : employees) {
            String wxId = employee.getWxId();
            if (!StringUtils.isEmpty(wxId)) {
                //发送消息提醒
                sendMessage(splicingJson(employee, "\",\n" +
                        "  \"msgtype\": \"markdown\",\n" +
                        "  \"agentid\": 1000002,\n" +
                        "  \"markdown\": {\n" +
                        "    \"content\": \"安全巡检系统\n" +
                        "    日期: " + format + "\n" +
                        "    审批提醒\n" +
                        "    请及时审批\n" +
                        "    请点击进入：[审批](" + employee.getInspection().getCodeUrl() + ")\n" +
                        "    \"").toString());
            }
        }

    }


    @Override
    public List<Employee> selectByUnifiedRemindId(Long id) {
        return employeeDao.selectByDepartment(id);
    }

    /**
     * 根据漏检查询人员信息
     *
     * @param id
     * @return
     */
    @Override
    public List<Employee> selectByOmissionId(Long id) {
        return employeeDao.selectByOmissionId(id);
    }

    /**
     * 审批通知管理员
     *
     * @param review
     */
    @Override
    public void sendAdminApprove(Boolean review, Long empId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        Employee employee = employeeDao.selectByPrimaryKey(empId);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\",\n" +
                "  \"msgtype\": \"markdown\",\n" +
                "  \"agentid\": 1000002,\n" +
                "  \"markdown\": {\n" +
                "    \"content\": \"安全巡检系统\n" +
                "    日期: " + format + "\n" +
                "    审核提醒\n");

        if (review) {
            stringBuilder.append(
                    "    已通过\n" +
                            "    \"");
            StringBuilder sb = splicingJson(employee, stringBuilder.toString());
            sendMessage(sb.toString());
        } else {
            stringBuilder.append(
                    "    未通过\n" +
                            "    \"");
            StringBuilder sb = splicingJson(employee, stringBuilder.toString());
            sendMessage(sb.toString());
        }
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


    /**
     * 发送消息提醒
     */
    public void sendMessage(String msg) {
        //获取企业微信token
        JSONObject token = WXToken.getToken(WXToken.corpId, WXToken.corpsecret, WXToken.url);
        String access_token = (String) token.get("access_token");
        //获取url
        String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + access_token;
        try {
            if (!StringUtils.isEmpty(msg)) {
                //企业微信消息发送提醒
                MultiRequest.doPostData(url, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拼接json
     *
     * @param employee
     * @return
     */
    public StringBuilder splicingJson(Employee employee, String msg) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(sb.toString())) {
            sb.append("{\n" +
                    "  \"touser\": ").append("\"" + employee.getWxId());
        } else {
            sb.append("|").append(employee.getWxId());
        }
        sb.append(msg);
        return sb;
    }
}
