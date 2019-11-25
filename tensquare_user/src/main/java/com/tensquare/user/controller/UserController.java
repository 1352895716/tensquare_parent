package com.tensquare.user.controller;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import util.IdWorker;

import java.util.Date;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    IdWorker idWorker;

    //发送手机验证码
    @RequestMapping(value = "/sendsms/{mobile}",method = RequestMethod.POST)
    public Result sendSms(@PathVariable String mobile){
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK,"发送成功");
    }

    //用户注册
    @RequestMapping(value = "/register/{code}",method = RequestMethod.POST)
    public Result regist(@PathVariable("code") String code,@RequestBody User user){

        //1.得到缓存中的验证码
        String redisCode = (String) redisTemplate.opsForValue().get("checkcode_"+user.getMobile());
        if(redisCode.isEmpty()){
            return new Result(false,StatusCode.ERROR,"请先获取验证码");
        }
        if(!redisCode.equals(code)){
            return new Result(false,StatusCode.ERROR,"验证码错误！");
        }
        user.setId(idWorker.nextId()+"");
        user.setRegdate(new Date());
        userService.save(user);
        return new Result(true,StatusCode.OK,"添加成功！");
    }
}
