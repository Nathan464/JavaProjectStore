package com.nathan.store.controller;

import com.nathan.store.entity.Address;
import com.nathan.store.service.IAddressService;
import com.nathan.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController {
    @Autowired(required = false)
    private IAddressService addressService;

    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(HttpSession session, Address address) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addNewAddress(uid, username, address);
        return new JsonResult<>(success);
    }

    @RequestMapping({"", "/"})
    public JsonResult<List<Address>> getByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<Address> list = addressService.getByUid(uid);
        return new JsonResult<>(success, list);
    }

    // RestFul风格请求编写
    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid,
                                       HttpSession session) {
        addressService.setDefault(aid, getUidFromSession(session),
                getUsernameFromSession(session));
        return new JsonResult<>(success);
    }
}
