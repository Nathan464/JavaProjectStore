package com.nathan.store.mapper;

import com.nathan.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

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

    @Test
    public void findByUid(){
        List<Address> list = addressMapper.findByUid(7);
        System.out.println(list);
    }

    @Test
    public void findByAid(){
        Address address = addressMapper.findByAid(1);
        System.out.println(address);
    }

    @Test
    public void updateNonDefaultByUid(){
        Integer row = addressMapper.updateNonDefaultByUid(1);
        System.out.println(row);
    }

    @Test
    public void updateDefaultByAid(){
        addressMapper.updateDefaultByAid(1,"admin",new Date());
    }

}
