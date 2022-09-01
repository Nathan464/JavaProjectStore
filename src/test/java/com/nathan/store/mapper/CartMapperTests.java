package com.nathan.store.mapper;

import com.nathan.store.entity.Cart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@SpringBootTest // 标注测试类
@RunWith(SpringRunner.class)
public class CartMapperTests {
    @Autowired(required = false)
    private CartMapper cartMapper;

    @Test
    public void insert() {
        Cart cart = new Cart();
        cart.setUid(7);
        cart.setPid(10000007);
        cart.setNum(2);
        cart.setPrice(32999L);
        cartMapper.insert(cart);
    }

    @Test
    public void update() {
        cartMapper.updateNumByCid(1, 4, "admin", new Date());
    }

    @Test
    public void findByUidAndPid() {
        Cart cart = cartMapper.findByUidAndPid(7, 10000007);
        System.out.println(cart);
    }

    @Test
    public void findVOByUid() {
        System.out.println(cartMapper.findVOByUid(7));
    }
}