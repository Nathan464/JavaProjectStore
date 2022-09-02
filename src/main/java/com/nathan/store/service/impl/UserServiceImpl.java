package com.nathan.store.service.impl;

import com.nathan.store.entity.User;
import com.nathan.store.mapper.UserMapper;
import com.nathan.store.service.IUserService;
import com.nathan.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

// 用户业务层实现类
@Service
public class UserServiceImpl implements IUserService {
    private final UserMapper userMapper;

    // 构造器注入bean
    @Autowired(required = false)
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

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

    @Override
    public User login(String username, String password) {
        User result = userMapper.findByUsername(username);
        // 用户不存在抛异常
        if (result == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        // 匹配密码: 将密码以相同的规则加密，再与数据库匹配
        String salt = result.getSalt();
        String md5Password = getMD5Password(password,salt);
        if (!result.getPassword().equals(md5Password)){
            throw new PasswordNotMatchException("密码错误");
        }
        // 判断is_delete字段是否为1-已删除
        if (result.getIsDelete()==1){
            throw new UsernameNotFoundException("用户数据不存在");
        }
        // new一个新对象，辅助其它页面的数据展示，且应只封装所需信息，减少数据量
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User user = userMapper.findByUid(uid);
        if (user==null||user.getIsDelete()==1){
            throw new UsernameNotFoundException("用户不存在");
        }
        String oldMd5Password = getMD5Password(oldPassword,user.getSalt());
        if(!user.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("原密码错误");
        }
        String newMd5Password = getMD5Password(newPassword,user.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid,newMd5Password,username,new Date());
        if (rows!=1){
            throw new UpdateException("更新数据时出现异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result==null||result.getIsDelete()==1){
            throw new UsernameNotFoundException("用户未找到");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setEmail(result.getEmail());
        user.setPhone(result.getPhone());
        user.setGender(result.getGender());
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        // 方法传入的user对象包含phone、email、gender，需手动封装uid、username
        User result = userMapper.findByUid(uid);
        if (result==null||result.getIsDelete()==1){
            throw new UsernameNotFoundException("用户未找到");
        }
        user.setUid(uid);
        user.setUsername(username);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        if (rows!=1){
            throw new UpdateException("更新数据产生异常");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        // 判断用户数据是否存在
        User result = userMapper.findByUid(uid);
        if (result==null||result.getIsDelete()==1){
            throw new UsernameNotFoundException("用户未找到");
        }
        Integer rows = userMapper.updateAvatarByUid(uid,avatar,username,new Date());
        if (rows!=1){
            throw new UpdateException("更新数据出现异常");
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
