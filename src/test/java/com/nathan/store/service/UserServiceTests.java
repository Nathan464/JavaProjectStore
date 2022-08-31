package com.nathan.store.service;

import com.nathan.store.entity.User;
import com.nathan.store.mapper.UserMapper;
import com.nathan.store.service.ex.ServiceException;
import com.nathan.store.service.ex.UpdateException;
import com.nathan.store.service.ex.UsernameNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest // 标注测试类
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Autowired(required = false)
    private IUserService userService;
    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("gang");
            user.setPassword("123456");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void login(){
        User user = userService.login("gang","123456");
        System.out.println(user);
    }

    @Test
    public void changePassword(){
        userService.changePassword(7,"gang","123456","321");
    }

    @Test
    public void getByUid() {
        System.out.println(userService.getByUid(7));
    }

    @Test
    public void changeInfo() {
        User user = new User();
        user.setPhone("144425216");
        user.setEmail("gang@outlook.com");
        user.setGender(1);
        userService.changeInfo(7,"gang",user);
    }

    @Test
    public void changeAvatar(){
        userService.changeAvatar(7,
                "C:/Users/l1979/Desktop/mmexport1572852158194.jpg",
                "gang");
    }
}
