package com.nathan.store.mapper;

import com.nathan.store.entity.District;

import java.util.List;

public interface DistrictMapper {
    /**
     * 根据父代号查询
     *
     * @param parent 代号
     * @return 区域列表
     */
    List<District> findByParent(String parent);

    String findNameByCode(String code);
}
