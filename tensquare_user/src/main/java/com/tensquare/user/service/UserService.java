package com.tensquare.user.service;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;
    public void sendSms(String moblie){
        //生成六位随机数
        String checkcode = RandomStringUtils.randomNumeric(6);
        //在缓存中留一份
        redisTemplate.opsForValue().set("checkcode_"+moblie,checkcode,6, TimeUnit.HOURS);
        //给用户发一份
        HashMap<String,String>map = new HashMap<>();

        map.put("mobile",moblie);
        map.put("checkcode",checkcode);
        rabbitTemplate.convertAndSend("sms",map);

        //在控制条显示一份，方便测试
        System.out.println("验证码为:"+checkcode);
    }

    public void save(User user){
        userDao.save(user);
    }
}
