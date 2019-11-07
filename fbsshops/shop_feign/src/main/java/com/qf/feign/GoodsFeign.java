package com.qf.feign;

import com.qf.entity.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Yss
 * @Date 2019/10/10 0010
 */

@FeignClient(value = "web-goods" )
public interface GoodsFeign {


    @RequestMapping("/goods/list")
    List<Goods> goodsList();

    @RequestMapping("/goods/insert")
    boolean goodsInsert(@RequestBody Goods goods);

    @RequestMapping("/goods/queryById")
    Goods queryById(@RequestParam("gid") Integer gid);

    @RequestMapping("/goods/delById")
    boolean delById(@RequestParam("gid") Integer gid);
}
