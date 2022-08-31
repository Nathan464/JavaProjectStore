package com.nathan.store.service;

import com.nathan.store.entity.District;

import java.util.List;

public interface IDistrictService {
    /**
     * 根据父代号查询区域信息（省市区）
     * @param parent 代号
     * @return 多个区域信息
     */
    List<District> getParent(String parent);
    String getNameByCode(String code);
}
