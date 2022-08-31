package com.nathan.store.service.impl;

import com.nathan.store.entity.Address;
import com.nathan.store.mapper.AddressMapper;
import com.nathan.store.service.IAddressService;
import com.nathan.store.service.IDistrictService;
import com.nathan.store.service.ex.*;
import org.apache.tomcat.JarScanType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class IAddressImpl implements IAddressService {
    private final AddressMapper addressMapper;
    @Autowired(required = false)
    public IAddressImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    // 添加用户的收货地址的业务层
    @Autowired(required = false)
    private IDistrictService districtService;
    @Value("${user.address.max-count}")  // properties读取数据
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount) {
            throw new AddressCountLimitException("收货地址超出上限");
        }

        //根据districtService获取数据将省市区添加到address
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);
        // 补全修改者、修改时间等信息
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());
        address.setCreatedUser(username);
        address.setCreatedTime(new Date());
        Integer row = addressMapper.insert(address);
        if (row != 1) {
            throw new InsertException("插入数据时发生异常");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        // 去除不需要展示的内容
        for (Address address : list) {
            address.setUid(null);
            //address.setAid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setTel(null);
            address.setCreatedTime(null);
            address.setIsDefault(null);
            address.setCreatedUser(null);
            address.setModifiedTime(null);
            address.setModifiedUser(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address address = addressMapper.findByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("地址未找到");
        }
        // 检测收货地址数据的归属人
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问数据");
        }
        // 先将所有地址设置为非默认
        Integer rows = addressMapper.updateNonDefaultByUid(uid);
        if (rows < 1) {
            throw new UpdateException("更新数据产生未知异常");
        }
        // 设置默认收货地址
        Integer row = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (row != 1) {
            throw new UpdateException("更新数据产生未知异常");
        }
    }
}
