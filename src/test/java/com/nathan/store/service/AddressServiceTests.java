package com.nathan.store.service;

import com.nathan.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest // 标注测试类
@RunWith(SpringRunner.class)
public class AddressServiceTests {
    @Autowired(required = false)
    private IAddressService addressService;

    @Test
    public void addNewAddress() {
        Address address = new Address();
        address.setPhone("22222");
        address.setName("nat");
        addressService.addNewAddress(1, "admin", address);
    }

    @Test
    public void setDefault(){
        addressService.setDefault(4,7,"admin");
    }

    @Test
    public void delete(){
        addressService.delete(4,7,"李瑞");
    }
}
