package com.nathan.store.controller;

import com.nathan.store.entity.District;
import com.nathan.store.service.IDistrictService;
import com.nathan.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("districts")
public class DistrictController extends BaseController {

    private final IDistrictService districtService;

    @Autowired
    public DistrictController(IDistrictService districtService) {
        this.districtService = districtService;
    }

    // districts开头的请求拦截到getByParent方法中
    @RequestMapping({"/", ""}) // districts后续跟/或空格都能拦截到
    public JsonResult<List<District>> getByParent(String parent) {
        List<District> list = districtService.getParent(parent);
        return new JsonResult<>(success, list);
    }
}
