package com.nathan.store.service.impl;

import com.nathan.store.entity.Cart;
import com.nathan.store.mapper.CartMapper;
import com.nathan.store.mapper.ProductMapper;
import com.nathan.store.service.ICartService;
import com.nathan.store.service.IProductService;
import com.nathan.store.service.ex.AccessDeniedException;
import com.nathan.store.service.ex.CartNotFoundException;
import com.nathan.store.service.ex.InsertException;
import com.nathan.store.service.ex.UpdateException;
import com.nathan.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
    // 购物车业务层依赖于商品和购物车的持久层
    @Autowired(required = false)
    private CartMapper cartMapper;
    @Autowired(required = false)
    private IProductService productService;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer num, String username) {
        // 查询商品是否存在
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        Date date = new Date();
        if (result == null) {  // 不存在则插入数据
            Cart cart = new Cart();
            // 补全数据
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(num);
            cart.setPrice(productService.findById(pid).getPrice());
            cart.setModifiedUser(username);
            cart.setModifiedTime(date);
            cart.setCreatedUser(username);
            cart.setCreatedTime(date);
            Integer row = cartMapper.insert(cart);
            if (row != 1) {
                throw new InsertException("插入数据时产生异常");
            }
        } else {  // 存在则更新数据
            Integer amount = result.getNum() + num;
            Integer row = cartMapper.updateNumByCid(result.getCid(), amount, username, date);
            if (row != 1) {
                throw new UpdateException("数据更新产生异常");
            }
        }
    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("尝试访问的购物车数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问数据");
        }
        Integer num = result.getNum() + 1;
        Date date = new Date();
        Integer row = cartMapper.updateNumByCid(cid, num, username, date);
        if (row != 1) {
            throw new UpdateException("更新数量时产生异常");
        }
        return num;
    }

    @Override
    public Integer subNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("尝试访问的购物车数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问数据");
        }
        Integer num = result.getNum() - 1;
        Date date = new Date();
        Integer row = cartMapper.updateNumByCid(cid, num, username, date);
        if (row != 1) {
            throw new UpdateException("更新数量时产生异常");
        }
        return num;
    }

    @Override
    public List<CartVO> getVOByCids(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findVOByCids(cids);
        list.removeIf(cart -> !cart.getUid().equals(uid));
        return list;
    }
}
