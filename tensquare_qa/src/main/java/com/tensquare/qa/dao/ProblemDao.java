package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    //jpa 查询方式
    //1基本crud  直接掉接口中的api
    //2复杂的查询  直接掉api 但是得传入匿名对象new Specification<T>()
    //3分页  传入pageable对象
    //4命名查询  在到层写接口（接口名称很重要）
    //5sql查询  （万能）
    //最新回答                                                                                                     //此处DESC后带分号会报错
    @Query(value = "SELECT * from tb_problem INNER JOIN tb_pl on id = problemid where labelid=? ORDER BY replytime DESC",nativeQuery = true)
    public Page<Problem> getNewProblemList(Integer label, Pageable pageable);

    //热门回答
    @Query(value = "select  * from tb_problem,tb_pl where id=problemid and labelid=? order by reply DESC",nativeQuery = true)
    public Page<Problem> hostlist(Integer labelid, Pageable pageable);


    //等待回答
    @Query(value = "select  * from tb_problem,tb_pl where id=problemid and labelid=? and reply=0 order by createtime DESC",nativeQuery = true)
    public Page<Problem> waitlist(Integer labelid, Pageable pageable);

}
