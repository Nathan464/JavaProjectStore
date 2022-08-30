package com.nathan.store.mapper;

import com.nathan.store.entity.Address;

public interface AddressMapper {
    /**
     * 插入收货地址
     * @param address 地址数据
     * @return 影响的行数
     */
    Integer insert(Address address);

    /**
     * 根据用户id统计收货地址
     * @param uid id
     * @return 收货地址总数
     */
    Integer countByUid(Integer uid);
}
