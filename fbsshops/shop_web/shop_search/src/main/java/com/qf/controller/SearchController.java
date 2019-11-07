package com.qf.controller;

import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * @author Yss
 * @Date 2019/10/16 0016
 */

@Controller
@RequestMapping("/search")
public class SearchController {


    @Autowired
    private ISearchService searchService;

    @RequestMapping("/query")
    @ResponseBody
    public List<Goods> query(String keyword,ModelMap map){
//        System.out.println("SearchController中/serarch/query内的keyword为："+keyword);
        List<Goods> goodsList = searchService.queryByKey(keyword);
//        System.out.println("SearchController中/serarch/query内的goodsList为："+goodsList);
        map.put("glist",goodsList);
        return goodsList;
    }


    @RequestMapping("/keyword")
    public String queryByKey(String keyword, ModelMap map){
//        System.out.println("SearchController中/serarch/keyword内的keyword为："+keyword);
        List<Goods> goodsList = searchService.queryByKey(keyword);
//        System.out.println("SearchController中/serarch/keyword内的goodsList为："+goodsList);
        map.put("glist",goodsList);
        return "searchList";
    }


    @RequestMapping("/insert")
    @ResponseBody
    public boolean insertSolr(@RequestBody Goods goods) {
       return searchService.insert(goods);
    }
}
