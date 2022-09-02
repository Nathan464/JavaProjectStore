package com.nathan.store.service.impl;

import com.nathan.store.entity.Product;
import com.nathan.store.mapper.ProductMapper;
import com.nathan.store.service.IProductService;
import com.nathan.store.service.ex.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    private final ProductMapper productMapper;

    @Autowired(required = false)
    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public List<Product> findHotList() {
        List<Product> list = productMapper.findHotList();
        for(Product product:list){
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return list;
    }

    @Override
    public Product findById(Integer id) {
        Product product = productMapper.findById(id);
        if (product == null) {
            throw new ProductNotFoundException("商品未找到");
        }
        product.setPriority(null);
        product.setCreatedUser(null);
        product.setCreatedTime(null);
        product.setModifiedUser(null);
        product.setModifiedTime(null);
        return product;
    }
}
