package com.nathan.store.controller;

import com.nathan.store.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController{
    private final IAddressService addressService;

    @Autowired
    public AddressController(IAddressService addressService) {
        this.addressService = addressService;
    }

}
