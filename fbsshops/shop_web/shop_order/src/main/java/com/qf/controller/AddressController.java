package com.qf.controller;

import com.qf.aop.Islogin;
import com.qf.entity.Address;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Yss
 * @Date 2019/10/28 0028
 */

@Controller
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private IAddressService addressService;


    @Islogin(mustLogin = true)
    @RequestMapping("/insert")
    @ResponseBody
    public String insertAddress(Address address, User user){
        address.setUid(user.getId());
        addressService.insertAddress(address);
        return "succ";
    }
}
