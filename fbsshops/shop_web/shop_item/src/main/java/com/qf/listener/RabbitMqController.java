package com.qf.listener;

import com.qf.controller.ItemController;
import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yss
 * @Date 2019/10/19 0019
 */

@Component
public class RabbitMqController {

    @Autowired
    private Configuration configuration;


    @RabbitListener(queues = "itema_queue")
    public void goodsMsgHandler(Goods goods){
//        System.out.println("ItemController中/item/createHtml中接收的goods为："+goods);
        String path = ItemController.class.getResource("/static/html").getPath();
//        System.out.println("ItemController中/item/createHtml中创建的Path为："+path);

        try(
                Writer writer = new FileWriter(path+"/"+goods.getId()+".html");
        ) {
            Template template = configuration.getTemplate("goods.ftl");

            Map<String,Object> map = new HashMap();
            map.put("goods",goods);

            template.process(map,writer);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.out.println("ItemController中/item/createHtml中页面创建完成：");
    }

}
