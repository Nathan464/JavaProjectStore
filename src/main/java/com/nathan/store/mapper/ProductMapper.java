package com.nathan.store.mapper;

import com.nathan.store.entity.Product;

import java.util.List;

public interface ProductMapper {
    List<Product> findHotList();
    Product findById(Integer id);
}
