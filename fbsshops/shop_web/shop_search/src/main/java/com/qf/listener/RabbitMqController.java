package com.qf.listener;

import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Yss
 * @Date 2019/10/19 0019
 */

@Component
public class RabbitMqController {

    @Autowired
    private ISearchService searchService;

    @RabbitListener(queues = "searcha_queue")
    public void goodsMsgHandler(Goods goods){

//        System.out.println("Search服务中的RabbitMqController中的search_queue接收到的数据goods为："+goods);
        searchService.insert(goods);
//        System.out.println("Search服务中的RabbitMqController中的search_queue已经经过添加");

    }

}
