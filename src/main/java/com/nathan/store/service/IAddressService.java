package com.nathan.store.service;

import com.nathan.store.entity.Address;

public interface IAddressService {
    void addNewAddress(Integer uid, String username, Address address);
}
