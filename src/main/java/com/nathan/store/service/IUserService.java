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

    /**
     * 更改密码
     * @param uid id
     * @param username 用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePassword(Integer uid, String username, String oldPassword, String newPassword);

    /**
     * 获取用户信息
     * @param uid id
     * @return 查询到返回用户信息，否则为空
     */
    User getByUid(Integer uid);

    /**
     * 更改用户信息
     * @param uid id
     * @param username 用户名
     * @param user User对象
     */
    void changeInfo(Integer uid, String username, User user);

    /**
     * 更改用户头像
     * @param uid id
     * @param avatar 头像文件路径
     * @param username 用户名
     */
    void changeAvatar(Integer uid, String avatar,String username);
}
