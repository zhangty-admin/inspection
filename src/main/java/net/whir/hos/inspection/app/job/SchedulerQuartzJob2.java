package net.whir.hos.inspection.app.job;

import com.alibaba.fastjson.JSONObject;
import net.whir.hos.inspection.commons.utils.MultiRequest;
import net.whir.hos.inspection.commons.utils.WXToken;
import net.whir.hos.inspection.pc.bean.Employee;
import net.whir.hos.inspection.pc.service.EmployeeService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 实现Job接口
 *
 * @Author: zty
 * @Date: 2020/5/7 1:52 下午
 */

@DependsOn("springContextUtil")
public class SchedulerQuartzJob2 implements Job {

    @Autowired
    private EmployeeService employeeService;

    private void before() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("任务开始执行: " + simpleDateFormat.format(new Date()));
    }

    @Override
    public void execute(JobExecutionContext arg0) {
        before();
        System.out.println("开始：" + arg0.getTrigger().getKey().getName() + ":" + arg0.getTrigger().getKey().getGroup());

        // TODO 业务
        Long id = (Long) arg0.getTrigger().getJobDataMap().get("id");
        if (!StringUtils.isEmpty(id)) {
            //获取企业微信token
            JSONObject token = WXToken.getToken(WXToken.corpId, WXToken.corpsecret, WXToken.url);
            String access_token = (String) token.get("access_token");
            //获取url
            String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + access_token;

            //查询人员 发送消息 拼接模版
            List<Employee> employees = this.employeeService.selectByOmissionId(id);
            StringBuilder sb = new StringBuilder();

            for (Employee employee : employees) {
                String wxId = employee.getWxId();
                if (!StringUtils.isEmpty(wxId)) {
                    SplicingJson(sb, employee);
                }
            }
            try {
                MultiRequest.doPostData(url, sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        after();
    }

    private void after() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("结束：" + simpleDateFormat.format(new Date()));
    }

    private void SplicingJson(StringBuilder sb, Employee employee) {
        if (StringUtils.isEmpty(sb.toString())) {
            sb.append("{\n" +
                    "  \"touser\": ").append("\"" + employee.getWxId());
        } else {
            sb.append("|").append(employee.getWxId());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        sb.append("\",\n" +
                "  \"msgtype\": \"markdown\",\n" +
                "  \"agentid\": 1000002,\n" +
                "  \"markdown\": {\n" +
                "    \"content\": \"安全巡检系统\n" +
                "    漏检提醒\n" +
                "    检查未扫码、请及时完成\n" +
                "    检查场所: " + employee.getDepartment().getName() + "\n" +
                "    计划巡检日期: " + format + "\n" +
                "    请点击进入：[安全巡检](" + employee.getInspection().getCodeUrl() + ")\n" +
                "    \"");
    }

}