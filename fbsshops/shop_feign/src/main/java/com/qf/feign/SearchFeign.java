package com.qf.feign;

import com.qf.entity.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Yss
 * @Date 2019/10/16 0016
 */

@FeignClient(value = "web-search" )
public interface SearchFeign {


    @RequestMapping("/search/insert")
    boolean insertSolr(@RequestBody Goods goods);


    @RequestMapping("/search/query")
    List<Goods> query(String keyword);
}
