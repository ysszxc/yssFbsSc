package com.qf.controller;

import com.qf.aop.Islogin;
import com.qf.entity.Shopcart;
import com.qf.entity.User;
import com.qf.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Yss
 * @Date 2019/10/25 0025
 */

@Controller
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private ICartService cartService;



    @Islogin
    @RequestMapping("/insert")
    public String insert(@CookieValue(value = "CART_TOKEN",required = false) String cartToken,
                         Integer gid, Integer number, User user, HttpServletResponse response){
        System.out.println("gid = "+gid+"---number = "+number);
        System.out.println("user:--"+ user);

        cartToken = cartService.insertCart(gid, number, user,cartToken);
        if (cartToken != null){
            Cookie cookie = new Cookie("CART_TOKEN",cartToken);
            cookie.setMaxAge(60 * 60 * 24 * 365);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return "succ";
    }


    @Islogin
    @RequestMapping("/list")
    @ResponseBody
    public List<Shopcart> queryShopCaer(@CookieValue(value = "CART_TOKEN",required = false) String cartToken,User user){
        System.out.println("cartToken---:"+cartToken+ "----user:---"+user);
        List<Shopcart> shopcarts = cartService.queryShopCart(cartToken, user);
        return shopcarts;
    }


    @Islogin
    @RequestMapping("/merge")
    public String mergeShopCart(
            String returnUrl,
            @CookieValue(value = "CART_TOKEN",required = false) String cartToken,
            User user,HttpServletResponse response){
        System.out.println("returnUrl----"+returnUrl);

        int result = cartService.mergeShopCart(cartToken, user);
        if (result > 0){

            Cookie cookie = new Cookie("CART_TOKEN",null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return "redirect:"+returnUrl;
    }

    @Islogin
    @RequestMapping("/showlist")
    public String showlist(@CookieValue(value = "CART_TOKEN",required = false) String cartToken,
                           User user, ModelMap map){

        List<Shopcart> shopcarts = cartService.queryShopCart(cartToken, user);
        map.put("shopcarts",shopcarts);

        return "cartlist";
    }


    @RequestMapping("/queryBuGid")
    @ResponseBody
    public List<Shopcart>  queryBuGid(@RequestParam("gid") Integer[] gid, @RequestParam("uid") Integer uid){
        for (Integer ml : gid) {
            System.out.println(ml+"----5959----"+uid);
        }
        List<Shopcart> shopcarts = cartService.queryBuGid(gid, uid);
        System.out.println("shopcarts4949:"+shopcarts);
        return shopcarts;
    }


    @RequestMapping("/queryByIds")
    @ResponseBody
    public List<Shopcart> queryByIds(@RequestParam("ids") Integer[] ids){
        return cartService.queryByIds(ids);
    }


    @RequestMapping("/deleteByIds")
    @ResponseBody
    public boolean deleteByIds(@RequestParam("ids") Integer[] ids){
        return cartService.deleteByIds(ids) > 0;
    }

}
