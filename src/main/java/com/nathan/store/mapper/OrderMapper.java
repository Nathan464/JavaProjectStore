package com.nathan.store.mapper;

import com.nathan.store.entity.Order;
import com.nathan.store.entity.OrderItem;

public interface OrderMapper {
    /**
     * 插入订单数据
     *
     * @param order 订单
     * @return 受影响行数
     */
    Integer insertOrder(Order order);

    /**
     * 插入订单商品数据
     *
     * @param orderItem 订单商品
     * @return 受影响的行数
     */
    Integer insertOrderItem(OrderItem orderItem);
}
