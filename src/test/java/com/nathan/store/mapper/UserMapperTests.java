package com.nathan.store.mapper;

import com.nathan.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest // 标注测试类
@RunWith(SpringRunner.class)
public class UserMapperTests {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setUsername("tim");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByUsername(){
        User user = userMapper.findByUsername("tim");
        System.out.println(user);
    }
    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(4,"123456","manager",new Date());
    }

    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(4));
    }

    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(7);
        user.setPhone("12222223434");
        user.setEmail("12213@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }
}
