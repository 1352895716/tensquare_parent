package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.mongodb.repository.MongoRepository;
//菲关系型数据库没有所谓的复杂查询
public interface SpitDao extends MongoRepository<Spit,String> {
}
