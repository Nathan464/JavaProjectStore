package com.nathan.store.service;

import com.nathan.store.entity.User;

// 用户模块业务层
public interface IUserService {
    /**
     * 用户注册方法
     * @param user 用户
     */
    void reg(User user);
}
