package net.whir.hos.inspection.security.service;

import net.whir.hos.inspection.commons.entity.User;

/**
 * @Author: zty
 * @Date: 2020/4/7 10:14 上午
 */
public interface UserService {

    /**
     * 验证登陆
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);
}
