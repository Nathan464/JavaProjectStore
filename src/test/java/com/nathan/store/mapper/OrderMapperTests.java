package com.nathan.store.mapper;

import com.nathan.store.entity.Address;
import com.nathan.store.entity.Order;
import com.nathan.store.entity.OrderItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest // 标注测试类
@RunWith(SpringRunner.class)
public class OrderMapperTests {
    @Autowired(required = false)
    private OrderMapper orderMapper;

    @Test
    public void insertOder() {
        Order order = new Order();
        order.setUid(7);
        order.setRecvName("gang");
        orderMapper.insertOrder(order);
    }

    @Test
    public void insertOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOid(1);
        orderItem.setPid(10000001);
        orderItem.setTitle("广博(GuangBo)10本装40张A5牛皮纸记事本子日记本办公软抄本GBR0731");
        orderMapper.insertOrderItem(orderItem);
    }
}