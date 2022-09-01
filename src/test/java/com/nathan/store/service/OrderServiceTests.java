package com.nathan.store.service;

import com.nathan.store.entity.Order;
import com.nathan.store.service.impl.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest // 标注测试类
@RunWith(SpringRunner.class)
public class OrderServiceTests {
    @Autowired(required = false)
    private IOrderService orderService;

    @Test
    public void create() {
        Integer[] cids = {3,4};
        Order order = orderService.create(5,cids,7,"gang");
        System.out.println(order);
    }
}