package com.nathan.store.mapper;

import com.nathan.store.entity.User;

// 用户持久层接口
public interface UserMapper {
    /**
     * 插入数据
     * @param user 用户数据
     * @return 返回受影响行数（增删改影响返回行数，用于判断sql是否执行成功
     */
    Integer insert(User user);

    /**
     * 根据用户名查询用户数据
     * @param username 用户名
     * @return 查询成功返回user，错误则返回null
     */
    User findByUsername(String username);
}
