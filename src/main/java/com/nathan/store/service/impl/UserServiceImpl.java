package com.nathan.store.service.impl;

import com.nathan.store.entity.User;
import com.nathan.store.mapper.UserMapper;
import com.nathan.store.service.IUserService;
import com.nathan.store.service.ex.InsertException;
import com.nathan.store.service.ex.UsernameDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

// 用户业务层实现类
@Service
public class UserServiceImpl implements IUserService {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Override
    public void reg(User user) {
        // 通过user获得username，调用findByUsername判断是否被注册过
        User result = userMapper.findByUsername(user.getUsername());
        if (result!=null){
            throw new UsernameDuplicatedException("用户名已存在");
        }
        // 加密: 随机串 + password + 随机串-->md5连续加载三次
        // 盐值：随机串
        String password = user.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMD5Password(password,salt);

        // 补充用户数据
        user.setPassword(md5Password);
        // salt加入数据库以保证所得md5Password一致
        user.setSalt(salt);
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        // 为空时执行用户注册
        Integer rows = userMapper.insert(user);
        if (rows!=1){
            throw new InsertException("用户注册过程中出现未知异常");
        }
    }

    // md5加密密码
    private String getMD5Password(String password, String salt) {
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
