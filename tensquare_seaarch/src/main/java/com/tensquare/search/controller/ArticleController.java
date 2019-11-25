package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
@CrossOrigin

public class ArticleController {

    @Autowired
    ArticleService articleService;

    //增加文章
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        return  articleService.addArticle(article);
    }


    //搜索文章
    @RequestMapping(value = "/{key}/{page}/{size}",method = RequestMethod.GET)
    public Result findByKey(@PathVariable String key,@PathVariable int page,@PathVariable int size){
        return articleService.searchArticle(key,page,size);
    }

}
