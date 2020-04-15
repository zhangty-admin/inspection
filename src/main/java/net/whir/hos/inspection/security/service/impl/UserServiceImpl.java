package net.whir.hos.inspection.security.service.impl;

import net.whir.hos.inspection.commons.entity.User;
import net.whir.hos.inspection.security.dao.UserDao;
import net.whir.hos.inspection.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @Author: zty
 * @Date: 2020/4/7 10:14 上午
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    /**
     * 验证登陆
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public User login(String username, String password) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username).andEqualTo("password",password);
        return userDao.selectOneByExample(example);
    }
}
