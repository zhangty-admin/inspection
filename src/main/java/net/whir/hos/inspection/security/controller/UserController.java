package net.whir.hos.inspection.security.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.commons.entity.User;
import net.whir.hos.inspection.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequestMapping("/login")
@Api(description = "账户操作")

/**
 * @Author: zty
 * @Date: 2020/4/7 10:14 上午
 */
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "验证登陆")
    @GetMapping
    public Result login(HttpServletRequest request, HttpServletResponse response, @RequestParam String username, @RequestParam String password) {
        boolean boo = preHandle(request, response);
        if (boo) {
            User user = userService.login(username);
            if (StringUtils.isEmpty(user)) {
                return new Result(false, StatusCode.LOGINERROR, "账号不存在");
            }
            // 前端传回的密码实际为用户输入的：用户名（小写）+ 密码（小写）组合的字符串生成的md5值
            // 此处先通过后台保存的正确的用户名和密码计算出正确的md5值，然后和前端传回来的作比较
            String md5info = password.toLowerCase();
            String realPassword = DigestUtils.md5DigestAsHex(md5info.getBytes());
            if (!user.getPassword().equals(realPassword)) {
                return new Result(false, StatusCode.LOGINERROR, "账号密码错误");
            }

            // 校验通过时，在session里放入一个标识
            // 后续通过session里是否存在该标识来判断用户是否登录
            request.getSession().setAttribute("loginName", user.getUsername());

            return new Result(true, StatusCode.OK, "登陆成功", user);
        }
        return new Result(false, StatusCode.ACCESSERROR, "未登录");
    }

    /**
     * 在请求被处理之前调用
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response) {
        // 检查每个到来的请求对应的session域中是否有登录标识
        Object loginName = request.getSession().getAttribute("loginName");
        if (null == loginName || !(loginName instanceof String)) {
            return false;
        }
        String userName = (String) loginName;
        System.out.println("当前用户已登录，登录的用户名为： " + userName);
        return true;
    }

    /**
     * 注销登录
     *
     * @param request
     * @return
     */
    @PostMapping("/loginOut")
    public Result loginOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return new Result(true, StatusCode.OK, "注销成功");
    }

}
