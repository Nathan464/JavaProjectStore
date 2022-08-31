package com.nathan.store.service;

import com.nathan.store.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> findHotList();
    Product findById(Integer id);
}
