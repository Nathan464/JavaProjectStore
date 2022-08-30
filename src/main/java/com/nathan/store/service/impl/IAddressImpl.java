package com.nathan.store.service.impl;

import com.nathan.store.entity.Address;
import com.nathan.store.mapper.AddressMapper;
import com.nathan.store.service.IAddressService;
import com.nathan.store.service.ex.AddressCountLimitException;
import com.nathan.store.service.ex.InsertException;
import org.apache.tomcat.JarScanType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class IAddressImpl implements IAddressService {
    private final AddressMapper addressMapper;

    @Autowired(required = false)
    public IAddressImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Value("${user.address.max-count}")  // properties读取数据
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount) {
            throw new AddressCountLimitException("收货地址超出上限");
        }
        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);
        // 补全修改者、修改时间等信息
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());
        address.setCreatedUser(username);
        address.setCreatedTime(new Date());
        Integer row = addressMapper.insert(address);
        if (row!=1){
            throw new InsertException("插入数据时发生异常");
        }
    }
}
