package com.nathan.store.service;

import com.nathan.store.entity.District;
import com.nathan.store.mapper.DistrictMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DistrictServiceTests {
    @Autowired(required = false)
    private IDistrictService districtService;

    @Test
    public void findByParent() {
        List<District> list = districtService.getParent("86");
        for (District d : list) {
            System.out.println(d);
        }
    }
}
