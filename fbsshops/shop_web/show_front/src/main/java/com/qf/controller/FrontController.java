package com.qf.controller;

import com.qf.entity.Goods;
import com.qf.entity.GoodsImages;
import com.qf.feign.GoodsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Yss
 * @Date 2019/10/19 0019
 */

@Controller
@RequestMapping("/front")
public class FrontController {

    @Autowired
    private GoodsFeign goodsFeign;

    @RequestMapping("/toindex")
    @ResponseBody
    public List<Goods> toIndex(ModelMap map){

        List<Goods> goodsList = goodsFeign.goodsList();

        for (Goods goods : goodsList) {

            for (GoodsImages goodsImages : goods.getGoodsImages()) {
                if (goodsImages.getIsfengmian() == 1){
                    goods.setFengmian(goodsImages.getUrl());
                }
            }
        }
        map.put("glist",goodsList);
        return goodsList;
}

    @RequestMapping("/jkjk")
    public String toIndex(){
        return "index";
    }
}
