package com.nathan.store.service;

import com.nathan.store.entity.User;

import javax.servlet.http.HttpSession;

// 用户模块业务层
public interface IUserService {
    /**
     * 用户注册方法
     * @param user 用户
     */
    void reg(User user);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 返回登录用户数据，用户不存在返回null
     */
    User login(String username, String password);


    void changePassword(Integer uid, String username, String oldPassword, String newPassword);
}
