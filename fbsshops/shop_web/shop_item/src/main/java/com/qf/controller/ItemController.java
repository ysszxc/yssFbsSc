package com.qf.controller;

import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yss
 * @Date 2019/10/17 0017
 */

@Controller
@RequestMapping("/item")
public class ItemController {


    @Autowired
    private Configuration configuration;

    @RequestMapping("/createHtml")
    @ResponseBody
    public boolean createHtml(@RequestBody Goods goods){

//        System.out.println("ItemController中/item/createHtml中创建的goods为："+goods.getId());
//
//
//        String path = ItemController.class.getResource("/static/html").getPath();
//        System.out.println("ItemController中/item/createHtml中创建的Path为："+path);
//
//        try(
//                Writer writer = new FileWriter(path+"/"+goods.getId()+".html");
//          ) {
//            Template template = configuration.getTemplate("goods.ftl");
//
//            Map<String,Object> map = new HashMap();
//            map.put("goods",goods);
//
//            template.process(map,writer);
//
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return false;
    }

}
