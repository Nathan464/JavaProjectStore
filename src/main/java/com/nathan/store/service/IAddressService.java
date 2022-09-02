package com.nathan.store.service;

import com.nathan.store.entity.Address;

import java.util.List;

public interface IAddressService {
    /**
     * 添加收货地址
     *
     * @param uid      用户id
     * @param username 用户名
     * @param address  地址信息
     */
    void addNewAddress(Integer uid, String username, Address address);

    /**
     * 根据uid查询收货地址
     *
     * @param uid 用户id
     * @return 用户所有地址集合
     */
    List<Address> getByUid(Integer uid);

    /**
     * 设置默认地址 0-不默认，1-默认
     *
     * @param aid      地址id
     * @param uid      用户id
     * @param username 修改者
     */
    void setDefault(Integer aid, Integer uid, String username);

    /**
     * 根据aid和uid删除地址
     *
     * @param aid      地址id
     * @param uid      用户id
     * @param username 修改者
     */
    void delete(Integer aid, Integer uid, String username);

    /**
     * 根据aid查询地址
     *
     * @param aid 地址id
     * @param uid 用户id
     * @return 返回地址信息
     */
    Address getByAid(Integer aid, Integer uid);
}
