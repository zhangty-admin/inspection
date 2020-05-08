package net.whir.hos.inspection.app.job;

import com.alibaba.fastjson.JSONObject;
import net.whir.hos.inspection.app.utils.MultiRequest;
import net.whir.hos.inspection.app.utils.WXToken;
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
public class SchedulerQuartzJob1 implements Job {

    @Autowired
    private EmployeeService employeeService;

    /*@Value("${WX.corpid}")*/
    private String corpId = "ww7d1b766a3b9fcfb7";

    /*@Value("${WX.corpsecret}")*/
    private String corpsecret = "dXkvUsnh94K2UEaZhKB77XBNhgxDfXSj9J-HY-Ye6f8";

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
            List<Employee> employees = this.employeeService.selectByunifiedremindId(id);
            //获取企业微信token
            StringBuilder sb = new StringBuilder();
            JSONObject token = WXToken.getToken(corpId, corpsecret, "https://qyapi.weixin.qq.com/cgi-bin/gettoken");
            String access_token = (String) token.get("access_token");
            String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + access_token;
            for (Employee employee : employees) {
                String wxId = employee.getWxId();
                if (!StringUtils.isEmpty(wxId)) {
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
                            "    带检查提醒\n" +
                            "    请扫码巡检\n" +
                            "    检查场所: " + employee.getDepartment().getName() + "\n" +
                            "    计划巡检日期: " + format + "\n" +
                            "    请点击进入：[安全巡检](" + employee.getInspection().getCodeUrl() + ")\n" +
                            "    \"");
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


}