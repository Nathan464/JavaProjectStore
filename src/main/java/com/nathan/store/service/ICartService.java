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

    /**
     * 根据uid查询购物车数据
     *
     * @param uid 用户id
     * @return 购物车中商品的集合
     */
    List<CartVO> getVOByUid(Integer uid);

    /**
     * 点击+增加购物车中商品数量
     *
     * @param cid      购物车id
     * @param uid      用户id
     * @param username 修改者
     * @return 受影响的行数
     */
    Integer addNum(Integer cid, Integer uid, String username);

    /**
     * 点击-增加购物车中商品数量
     *
     * @param cid      购物车id
     * @param uid      用户id
     * @param username 修改者
     * @return 受影响的行数
     */
    Integer subNum(Integer cid, Integer uid, String username);

    /**
     * 根据多个购物车id查询某个用户的多条数据
     *
     * @param uid  用户id
     * @param cids 多条购物车id
     * @return 购物车数据集合
     */
    List<CartVO> getVOByCids(Integer uid, Integer[] cids);

}
