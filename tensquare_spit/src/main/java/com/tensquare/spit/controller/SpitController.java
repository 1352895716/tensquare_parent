package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    SpitService spitService;

    //新增 吐槽
    @RequestMapping(method = RequestMethod.POST)
    public Result addSpit(@RequestBody Spit spit){


        spitService.save(spit);
        return new Result(true, StatusCode.OK,"新增成功！");
    }
    //查询 全部
    @RequestMapping(method = RequestMethod.GET)
    public Result getAll(){
        return new Result(true,StatusCode.OK,"查询成功！",spitService.findAll());
    }

    //根据id查询
    @RequestMapping(value = "/{spitId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId){
        return new Result(true,StatusCode.OK,"查询成功！",spitService.findById(spitId));
    }

    //吐槽点赞
    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.PUT)
    public Result thumbup(@PathVariable("spitId") String spitId){
        return  spitService.thumbup(spitId);
    }
}
