package com.qf.service;

import com.qf.entity.Shopcart;
import com.qf.entity.User;

import java.util.List;

/**
 * @author Yss
 * @Date 2019/10/25 0025
 */
public interface ICartService {

    String insertCart(Integer gid, Integer number, User user,String cartToken);

    List<Shopcart> queryShopCart(String cartToken,User user);

    int mergeShopCart(String cartToken,User user);

    List<Shopcart> queryBuGid( Integer[] gid,Integer uid);

    int deleteByIds(Integer[] ids);

    List<Shopcart> queryByIds(Integer[] ids);
}
