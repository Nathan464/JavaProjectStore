package com.nathan.store.mapper;

import com.nathan.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest // 标注测试类
@RunWith(SpringRunner.class)
public class AddressMapperTests {
    @Autowired(required = false)
    private AddressMapper addressMapper;

    @Test
    public void insert(){
        Address address = new Address();
        address.setUid(1);
        address.setPhone("111111");
        address.setName("nat");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid(){
        Integer count = addressMapper.countByUid(1);
        System.out.println(count);
    }

}
