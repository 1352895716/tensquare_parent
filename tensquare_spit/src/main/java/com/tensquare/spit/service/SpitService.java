package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;


import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SpitService {
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;
    //查询全部
    public List<Spit> findAll(){
        return spitDao.findAll();
    }
    //根据主键查询
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }
    //新增
    public void save(Spit spit) {
        spit.set_id(idWorker.nextId()+"");
        spit.setPublishtime(new Date());
        spit.setVisits(0);
        spit.setThumbup(0);
        spit.setShare(0);
        spit.setComment(0);
        spitDao.save(spit);
        //检查新增的吐槽有没有父节点，如果有父节点+1
        if(spit.getParentid()!=null&&spit.getParentid()!=""){
            //query---封装条件
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            //update---修改某个字段的
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }

    }
    //修改
    public void update(Spit spit){
        spitDao.save(spit);
    }
    //删除
    public void deleteById(String id){
        spitDao.deleteById(id);
    }

    //吐槽点赞
    public Result thumbup(String spitId){
        Spit spit = spitDao.findById(spitId).get();

        String userId = "1234567";
        //判断该用户是否点赞过
        Object object = redisTemplate.opsForValue().get("thumbup_"+userId+spitId);
        if(object!=null)
            return new Result(true, StatusCode.ERROR,"不能重复点赞");
        spit.setThumbup(spit.getThumbup()==null?1:spit.getThumbup()+1);
        //一个人不能点赞两次
        //思路：某人点赞后给她标记一下，（使用redis）
        redisTemplate.opsForValue().set("thumbup_"+userId+spitId,userId+spitId);
        spitDao.save(spit);
        return new Result(true, StatusCode.OK,"点赞成功！");
    }
}
