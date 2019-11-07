package com.qf.service;

import com.qf.entity.Orders;

import java.util.List;

/**
 * @author Yss
 * @Date 2019/10/27 0027
 */
public interface IOrderService {

    Orders insertOrder(Integer aid, Integer[] cartsid, Integer uid);

    List<Orders> queryByUid(Integer uid);

    Orders queryById(Integer oid);

    Orders queryByOrderId(String orderid);

    int updateOrderState(String orderid, Integer status);
}
