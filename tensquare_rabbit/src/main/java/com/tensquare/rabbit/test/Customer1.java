package com.tensquare.rabbit.test;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RabbitListener(queues = "aaa1")
@Component
public class Customer1 {//直接模式消费

    @RabbitHandler
    public void getMsg(String msg){//msg会接收来自消息队列的消息
        System.out.println("直接模式消费消息:"+msg);
    }

}

