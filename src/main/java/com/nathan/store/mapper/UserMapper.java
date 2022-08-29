package com.nathan.store.mapper;

import com.nathan.store.entity.User;

import java.util.Date;

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

    /**
     * 根据uid修改密码
     * @param uid id
     * @param password 新密码
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return 影响的数据库行数
     */
    Integer updatePasswordByUid(Integer uid, String password, String modifiedUser, Date modifiedTime);

    /**
     * uid查询用户
     * @param uid id
     * @return 成功返回对象，失败返回null
     */
    User findByUid(Integer uid);

    /**
     * 更新用户数据
     * @param user 用户
     * @return 返回受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * 根据uid更改头像
     * @param uid id
     * @param avatar 头像路径
     * @param modifiedUser 更改者
     * @param modifiedTime 更改时间
     * @return 受影响的行数
     */
    Integer updateAvatarByUid(Integer uid, String avatar,String modifiedUser,
                              Date modifiedTime);

}
