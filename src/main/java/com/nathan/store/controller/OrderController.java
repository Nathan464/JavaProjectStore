package com.nathan.store.controller;

import com.nathan.store.entity.Order;
import com.nathan.store.service.IOrderService;
import com.nathan.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("orders")
public class OrderController extends BaseController {
    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping("create")
    public JsonResult<Order> create(Integer aid, Integer[] cids, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Order data = orderService.create(aid, cids, uid, username);
        return new JsonResult<>(success, data);
    }
}
