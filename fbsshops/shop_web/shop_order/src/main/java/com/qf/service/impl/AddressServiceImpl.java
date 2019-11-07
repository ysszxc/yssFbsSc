package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.AddressMapper;
import com.qf.entity.Address;
import com.qf.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yss
 * @Date 2019/10/27 0027
 */
@Service
public class AddressServiceImpl implements IAddressService{

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> queryByUid(Integer uid) {

        System.out.println("uid,.,..,.,..,..,.:"+uid);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid",uid);
        List list = addressMapper.selectList(queryWrapper);
        for (Object o : list) {
            System.out.println("515151555:"+o);
        }
        return list;
    }

    @Override
    public int insertAddress(Address address) {

        System.out.println("123456address:"+address);
        if(address.getIsdefault() == 1){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("isdefault", 1);
            queryWrapper.eq("uid", address.getUid());
            Address address1 = addressMapper.selectOne(queryWrapper);
            System.out.println("1sdf6address1:"+address1);

            address1.setIsdefault(0);
            int i = addressMapper.updateById(address1);
            System.out.println("afdshfgds:"+i);
        }

        return addressMapper.insert(address);
    }

    @Override
    public Address queryById(Integer id) {
        return addressMapper.selectById(id);
    }
}
