package com.nathan.store.mapper;

import com.nathan.store.entity.Address;

import java.util.Date;
import java.util.List;

public interface AddressMapper {
    /**
     * 插入收货地址
     *
     * @param address 地址数据
     * @return 影响的行数
     */
    Integer insert(Address address);

    /**
     * 根据用户id统计收货地址
     *
     * @param uid id
     * @return 收货地址总数
     */
    Integer countByUid(Integer uid);

    /**
     * 以uid查询所有用户地址
     *
     * @param uid uid
     * @return 地址集合
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据aid查询收货地址
     *
     * @param Aid 收货地址id
     * @return 收货地址数据，查找为无返回null
     */
    Address findByAid(Integer Aid);

    /**
     * 根据用户的uid值来修改用户的收货地址设置为非默认
     *
     * @param uid 用户id
     * @return 返回受影响的函数
     */
    Integer updateNonDefaultByUid(Integer uid);

    /**
     *
     * @param aid 地址id
     * @param modifiedUser 更改者
     * @param modifiedTime 更改时间
     * @return 受影响行数
     */
    Integer updateDefaultByAid(Integer aid, String modifiedUser, Date modifiedTime);

    /**
     * 根据aid删除地址
     * @param aid id
     * @return 受影响行数
     */
    Integer deleteByAid(Integer aid);

    /**
     * 根据uid查询用户最新的地址数据
     * @param uid id
     * @return 最新地址数据
     */
    Address findLastModified(Integer uid);
}
