package com.tensquare.test;

import com.tensquare.rabbit.MQApplication;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MQApplication.class)
public class ProductTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void send1(){
        rabbitTemplate.convertAndSend("aaa1","33333");
                                                   //该aaa1为队列名
    }

    @Test
    public void sendMsg2(){
        rabbitTemplate.convertAndSend("zhiyou","","分裂模式测试");
    }
    @Test
    public void send3(){
        rabbitTemplate.convertAndSend("exchangeTopic","news.log","topic模式测试");
    }

}
