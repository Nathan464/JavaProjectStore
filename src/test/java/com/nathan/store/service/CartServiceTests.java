package com.nathan.store.service;

import com.nathan.store.entity.Cart;
import com.nathan.store.mapper.CartMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@SpringBootTest // 标注测试类
@RunWith(SpringRunner.class)
public class CartServiceTests {
    @Autowired(required = false)
    private ICartService cartService;

    @Test
    public void addToCart(){
        cartService.addToCart(7,10000007,3,"gang");
    }
}