package com.nathan.store.service;

import com.nathan.store.entity.Cart;
import com.nathan.store.mapper.CartMapper;
import com.nathan.store.vo.CartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;


@SpringBootTest // 标注测试类
@RunWith(SpringRunner.class)
public class CartServiceTests {
    @Autowired(required = false)
    private ICartService cartService;

    @Test
    public void addToCart(){
        cartService.addToCart(7,10000007,3,"gang");
    }

    @Test
    public void addNum(){
        cartService.addNum(5,7,"admin");
    }

    @Test
    public void subNum(){
        cartService.subNum(5,7,"admin");
    }
    @Test
    public void getVOByCids(){
        List<CartVO> list = cartService.getVOByCids(7,new Integer[]{4,5,6});
        for (CartVO cart : list){
            System.out.println(cart);
        }
    }
}