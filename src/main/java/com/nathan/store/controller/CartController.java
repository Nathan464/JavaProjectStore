package com.nathan.store.controller;

import com.nathan.store.service.ICartService;
import com.nathan.store.util.JsonResult;
import com.nathan.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController {
    @Autowired(required = false)
    private ICartService cartService;

    @RequestMapping("add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount,
                                      HttpSession session) {
        cartService.addToCart(getUidFromSession(session), pid, amount,
                getUsernameFromSession(session));
        return new JsonResult<>(success);
    }

    @RequestMapping({"","/"})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
        List<CartVO> data = cartService.getVOByUid(getUidFromSession(session));
        return new JsonResult<>(success, data);
    }
}
