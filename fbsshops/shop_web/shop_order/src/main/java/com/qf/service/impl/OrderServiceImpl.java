package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.OrderDetilMapper;
import com.qf.dao.OrdersMapper;
import com.qf.entity.*;
import com.qf.feign.CartFeign;
import com.qf.feign.GoodsFeign;
import com.qf.service.IAddressService;
import com.qf.service.IOrderService;
import com.qf.util.PriceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * @author Yss
 * @Date 2019/10/27 0027
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderDetilMapper orderDetilMapper;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private CartFeign cartFeign;

    @Autowired
    private GoodsFeign goodsFeign;

    @Override
    @Transactional
    public Orders insertOrder(Integer aid, Integer[] cartsid, Integer uid) {

        Address address = addressService.queryById(aid);
        List<Shopcart> shopcarts = cartFeign.queryByIds(cartsid);
        Orders orders = (Orders) new Orders()
                .setOrderid(UUID.randomUUID().toString())
                .setUid(uid)
                .setPerson(address.getPerson())
                .setAddress(address.getAddress())
                .setCode(address.getCode())
                .setPhone(address.getPhone())
                .setAllprice(BigDecimal.valueOf(PriceUtil.allprice(shopcarts)))
                .setCreateTime(new Date())
                .setStatus(0);
        ordersMapper.insert(orders);

        for (Shopcart shopcart : shopcarts) {
            OrderDetils orderDetils = (OrderDetils) new OrderDetils()
                    .setOid(orders.getId())
                    .setSubject(shopcart.getGoods().getSubject())
                    .setPrice(shopcart.getGoods().getPrice())
                    .setNumber(shopcart.getNumber())
                    .setGid(shopcart.getGid())
                    .setDetilsPrice(BigDecimal.valueOf(shopcart.getNumber()).multiply(shopcart.getGoods().getPrice()))
                    .setCreateTime(new Date())
                    .setStatus(0);

            orderDetilMapper.insert(orderDetils);
        }
        cartFeign.deleteByIds(cartsid);

        return orders;
    }

    @Override
    public List<Orders> queryByUid(Integer uid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        queryWrapper.orderByDesc("create_time");
        List<Orders> list = ordersMapper.selectList(queryWrapper);

        for (Orders orders : list) {
            QueryWrapper queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("oid", orders.getId());
            List<OrderDetils> detilsList = orderDetilMapper.selectList(queryWrapper2);

            for (OrderDetils orderDetils : detilsList) {
                Goods goods = goodsFeign.queryById(orderDetils.getGid());
                orderDetils.setGoods(goods);
            }

            orders.setOrderDetils(detilsList);
        }

        return list;
    }

    @Override
    public Orders queryById(Integer oid) {
        return ordersMapper.selectById(oid);
    }

    @Override
    public Orders queryByOrderId(String orderid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("orderid", orderid);
        return ordersMapper.selectOne(queryWrapper);
    }

    @Override
    public int updateOrderState(String orderid, Integer status) {

        Orders orders = this.queryByOrderId(orderid);
        orders.setStatus(status);
        return ordersMapper.updateById(orders);
    }
}
