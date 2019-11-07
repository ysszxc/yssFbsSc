package com.qf.feign;

import com.qf.entity.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Yss
 * @Date 2019/10/17 0017
 */

@FeignClient(value = "web-item")
public interface ItemFeign {

    @RequestMapping("/item/createHtml")
    boolean createHtml(@RequestBody Goods goods);
}
