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

    /**
     * 根据代码获取区名
     *
     * @param code 代码
     * @return 区名
     */
    String findNameByCode(String code);
}
