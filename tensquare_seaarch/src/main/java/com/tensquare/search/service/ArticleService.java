package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    public Result addArticle(Article article){
        articleDao.save(article);
        return new Result(true, StatusCode.OK,"添加成功");
    }
    //文章搜索
    public Result searchArticle(String key,int page,int size){
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Article> list = articleDao.findByTitleOrContentLike(key,key,pageable);
        return new Result(true,StatusCode.OK,"查询成功！",new PageResult<Article>(list.getTotalElements(),list.getContent()));
    }
}
