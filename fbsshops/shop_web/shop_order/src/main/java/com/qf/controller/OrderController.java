package com.qf.controller;

import com.qf.aop.Islogin;
import com.qf.entity.Address;
import com.qf.entity.Orders;
import com.qf.entity.Shopcart;
import com.qf.entity.User;
import com.qf.feign.CartFeign;
import com.qf.service.IAddressService;
import com.qf.service.IOrderService;
import com.qf.util.PriceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;


/**
 * @author Yss
 * @Date 2019/10/26 0026
 */

@Controller
@RequestMapping("/order")
public class OrderController {
    
    
    @Autowired
    private CartFeign cartFeign;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private IOrderService orderService;


    @Islogin(mustLogin = true)
    @RequestMapping("edit")
    public String orderedit(Integer[] cart_checkbox, User user, ModelMap map){

        System.out.println(user+"cart_checkbox:++++"+ Arrays.toString(cart_checkbox));

        List<Shopcart> shopcarts = cartFeign.queryBuGid(cart_checkbox, user.getId());

        List<Address> addresses = addressService.queryByUid(user.getId());

        double allprice = PriceUtil.allprice(shopcarts);

        map.put("shopcarts",shopcarts);
        map.put("addresses",addresses);
        map.put("allprice",allprice);
        System.out.println("shopcarts---:"+shopcarts);
        System.out.println("addresses---:"+addresses);
        System.out.println("allprice---:"+allprice);
        return "orderedit";
    }

    @Islogin(mustLogin = true)
    @RequestMapping("/insert")
    public String orderInsert(Integer aid, Integer[] cartids, User user){
        System.out.println("sidahiudh#########:"+user);

        Orders orders = orderService.insertOrder(aid, cartids, user.getId());

        return "redirect:http://localhost:16666/pay/alipay?oid=" + orders.getId();
    }

    @Islogin(mustLogin = true)
    @RequestMapping("/list")
    public String orderList(User user, ModelMap map){

        List<Orders> orders = orderService.queryByUid(user.getId());
        map.put("ordersList", orders);

        return "orderlist";
    }

}
