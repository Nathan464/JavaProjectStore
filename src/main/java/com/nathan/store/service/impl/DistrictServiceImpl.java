package com.nathan.store.service.impl;

import com.nathan.store.entity.District;
import com.nathan.store.mapper.DistrictMapper;
import com.nathan.store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {

    @Autowired(required = false)
    private DistrictMapper districtMapper;

    @Override
    public List<District> getParent(String parent) {
        List<District> list = districtMapper.findByParent(parent);
        // 无关数据可以设置为null减少数据量
        for (District d : list) {
            d.setId(null);
            d.setParent(null);
        }
        return list;
    }

    @Override
    public String getNameByCode(String code) {
        return districtMapper.findNameByCode(code);
    }
}
