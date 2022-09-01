package com.nathan.store.service.impl;

import com.nathan.store.entity.Address;
import com.nathan.store.entity.Order;
import com.nathan.store.entity.OrderItem;
import com.nathan.store.mapper.OrderMapper;
import com.nathan.store.service.IAddressService;
import com.nathan.store.service.ICartService;
import com.nathan.store.service.IOrderService;
import com.nathan.store.service.ex.InsertException;
import com.nathan.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired(required = false)
    private OrderMapper orderMapper;
    @Autowired(required = false)
    private IAddressService addressService;
    @Autowired(required = false)
    private ICartService cartService;

    @Override
    public Order create(Integer aid, Integer[] cids, Integer uid, String username) {
        List<CartVO> list = cartService.getVOByCids(uid, cids);
        Long totalPrice = 0L;

        for (CartVO cartVO:list){
            totalPrice = cartVO.getPrice()*cartVO.getNum();
        }
        Address address = addressService.getByAid(aid, uid);
        Order order = new Order();
        // 封装数据
        order.setUid(uid);
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        order.setStatus(0);
        order.setTotalPrice(totalPrice);
        order.setCreatedUser(username);
        order.setCreatedTime(new Date());
        order.setModifiedUser(username);
        order.setModifiedTime(new Date());
        order.setOrderTime(new Date());
        Integer row = orderMapper.insertOrder(order);
        if (row!=1){
            throw new InsertException("插入数据产生异常");
        }
        // 订单详细项的数据
        for (CartVO cartVO:list) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOid(order.getOid());
            orderItem.setPid(cartVO.getPid());
            orderItem.setTitle(cartVO.getTitle());
            orderItem.setPrice(cartVO.getPrice());
            orderItem.setNum(cartVO.getNum());
            orderItem.setCreatedUser(username);
            orderItem.setCreatedTime(new Date());
            orderItem.setModifiedUser(username);
            orderItem.setModifiedTime(new Date());
            Integer count = orderMapper.insertOrderItem(orderItem);
            if (count!=1){
                throw new InsertException("插入数据产生异常");
            }

        }
        return order;
    }
}
