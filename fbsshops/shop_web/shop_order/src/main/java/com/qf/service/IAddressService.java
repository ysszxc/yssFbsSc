package com.qf.service;

import com.qf.entity.Address;

import java.util.List;

/**
 * @author Yss
 * @Date 2019/10/27 0027
 */
public interface IAddressService {

    List<Address> queryByUid(Integer uid);

    int insertAddress(Address address);

    Address queryById(Integer aid);
}
