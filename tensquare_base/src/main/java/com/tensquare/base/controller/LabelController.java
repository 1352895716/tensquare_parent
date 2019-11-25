package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import util.IdWorker;

import java.util.List;

@RestController   //ResponseBody+Controller
@CrossOrigin     //用于跨域
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @RequestMapping(value = "/{labelId}",method = RequestMethod.GET)
    public Result getLabel(@PathVariable("labelId") String labelId){
        Label label = labelService.queryById(labelId);
        //System.out.println(label.toString());
        return new Result(true, StatusCode.OK,"查询成功！",labelService.queryById(labelId));
    }

    @RequestMapping("/list")
    public Result getAll(){
        List<Label>all = labelService.getAll();
        return new Result(true, StatusCode.OK,"查询成功！",all);
    }

    @RequestMapping(method = RequestMethod.POST)     //
    public Result addLabel(@RequestBody Label label){//从请求中接收json,所以是RequestBody
        labelService.addLabel(label);
        return new Result(true, StatusCode.OK,"增加成功！");
    }

    @RequestMapping(value = "/{labelId}",method = RequestMethod.PUT)
    public Result updLabel(@PathVariable("labelId") String labelId,@RequestBody Label label){//因为传过来的是json字符串，所以一定要加@requestBody
        label.setId(labelId);
        labelService.updLabel(label);
        System.out.println(label.toString());
        return new Result(true, StatusCode.OK,"修改成功！");
    }
    @RequestMapping(value = "/{labelId}",method = RequestMethod.DELETE)
    public Result delLable(@PathVariable("labelId")String labelId){
        labelService.delLabel(labelId);
        return new Result(true, StatusCode.OK,"删除成功！");
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label){
        List<Label> list= labelService.findSearch(label);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Label label,@PathVariable int page,@PathVariable int size){
        Page<Label> pageDate= labelService.pageQuery(label,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Label>(pageDate.getTotalElements(),pageDate.getContent()));

    }

}
