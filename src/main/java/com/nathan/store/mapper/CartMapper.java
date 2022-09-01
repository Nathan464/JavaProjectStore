package com.nathan.store.mapper;

import com.nathan.store.entity.Cart;
import com.nathan.store.vo.CartVO;

import java.util.Date;
import java.util.List;

public interface CartMapper {
    /**
     * 插入购物车数据
     * @param cart 购物车
     * @return 受影响行数
     */
    Integer insert(Cart cart);

    /**
     * 更新商品数量
     * @param cid 购物车id
     * @param num 数量
     * @param modifiedUser 修改者
     * @param modifiedTime 修改时间
     * @return 受影响行数
     */
    Integer updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime);

    /**
     * 根据用户id和商品id查询购物车中的数据
     * @param uid 用户id
     * @param pid 商品id
     * @return Cart购物车数据
     */
    Cart findByUidAndPid(Integer uid, Integer pid);

    List<CartVO> findVOByUid(Integer uid);
}
