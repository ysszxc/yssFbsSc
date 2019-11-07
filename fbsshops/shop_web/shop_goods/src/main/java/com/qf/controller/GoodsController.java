package com.qf.controller;

import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Yss
 * @Date 2019/10/10 0010
 */

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;


    @RequestMapping("/list")
    @ResponseBody
    public List<Goods> goodsList(){
        List<Goods> goodsList = goodsService.queryAllGoods();
//        for (Goods goods : goodsList) {
//            System.out.println("在shop_goods中/list的循环de结果为："+goods.getId());
//        }
//        System.out.println("在shop_goods中/list的结果为："+goodsList);
        return goodsList;
    }

    @RequestMapping("/insert")
    @ResponseBody
    public boolean goodsInsert(@RequestBody Goods goods){

//        System.out.println("goods/insert中的goods为："+goods);

        int result =  goodsService.goodsInsert(goods);

        return result > 0;
    }

    @RequestMapping("/queryById")
    @ResponseBody
    public Goods queryById(@RequestParam("gid") Integer gid){
        return   goodsService.queryById(gid);
    }

    @RequestMapping("/delById")
    @ResponseBody
    public boolean delById(@RequestParam("gid") Integer gid){
        System.out.println(gid);

        int result = goodsService.goodsDel(gid);

        if (result > 0){
            return true;
        }
        return false;
    }
}
