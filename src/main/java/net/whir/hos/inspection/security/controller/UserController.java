package net.whir.hos.inspection.security.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.commons.entity.User;
import net.whir.hos.inspection.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    public Result login(@RequestParam String username , @RequestParam String password) {
        User user = userService.login(username,password);
        if (StringUtils.isEmpty(user)) {
            return new Result(false, StatusCode.LOGINERROR,"账号密码错误");
        }
        return new Result(true,StatusCode.OK,"登陆成功",user);
    }

}
