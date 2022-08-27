package com.nathan.store.service;

import com.nathan.store.entity.User;
import com.nathan.store.mapper.UserMapper;
import com.nathan.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest // 标注测试类
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Autowired(required = false)
    private IUserService userService;
    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("huang");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void login(){
        User user = userService.login("li","123456");
        System.out.println(user);
    }
}
