package com.qf.shop_mails;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yss
 * @Date 2019/10/23 0023
 */


@Configuration
public class RabbitMQConfig {


    @Bean
    public Queue getQueue(){
        return new Queue("maila_queue");
    }

    @Bean
    public FanoutExchange getFanoutExchange(){
        return  new FanoutExchange("maila_exceptiion");
    }


    @Bean
    public Binding getBinding(){
        return BindingBuilder.bind(getQueue()).to(getFanoutExchange());
    }

}
