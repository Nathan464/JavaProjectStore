package com.nathan.store.service;

import com.nathan.store.vo.CartVO;

import java.util.List;

public interface ICartService {
    /**
     * 商品添加到购物车
     *
     * @param uid      用户id
     * @param pid      商品id
     * @param num      商品数量
     * @param username 修改者
     */
    void addToCart(Integer uid, Integer pid, Integer num, String username);

    List<CartVO> getVOByUid(Integer uid);
}
