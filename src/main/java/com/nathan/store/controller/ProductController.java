package com.nathan.store.controller;

import com.nathan.store.entity.Product;
import com.nathan.store.service.IProductService;
import com.nathan.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController extends BaseController{
    @Autowired(required = false)
    private IProductService productService;
    @RequestMapping("hot_list")
    public JsonResult<List<Product>> getHotList(){
        List<Product> list = productService.findHotList();
        return new JsonResult<>(success,list);
    }
    @GetMapping("{id}/details")
    public JsonResult<Product> getById(@PathVariable("id") Integer id) {
        Product data = productService.findById(id);
        return new JsonResult<>(success, data);
    }

}
