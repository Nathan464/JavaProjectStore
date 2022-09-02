package com.nathan.store.service;

import com.nathan.store.entity.Product;

import java.util.List;

public interface IProductService {
    /**
     * 查找热销商品
     *
     * @return 热销商品集合
     */
    List<Product> findHotList();

    /**
     * 根据id查询商品
     *
     * @param id 商品id
     * @return 商品信息
     */
    Product findById(Integer id);
}
