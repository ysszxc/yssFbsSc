package com.qf.shop_goods;

/**
 * @author Yss
 * @Date 2019/10/18 0018
 */

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {


    @Bean("searcha_queue")
    public Queue getQueue1(){
        return new Queue("searcha_queue");
    }


    @Bean("itema_queue")
    public Queue getQueue2(){
        return new Queue("itema_queue");
    }

    @Bean("goodsa_exchange")
    public FanoutExchange getExchange(){
        return new FanoutExchange("goodsa_exchange");
    }

    @Bean
    public Binding getBinding1(Queue searcha_queue , FanoutExchange goodsa_exchange){
        return  BindingBuilder.bind(searcha_queue).to(goodsa_exchange);
    }

    @Bean
    public Binding getBinding2(Queue itema_queue , FanoutExchange goodsa_exchange){
        return  BindingBuilder.bind(itema_queue).to(goodsa_exchange);
    }



}
