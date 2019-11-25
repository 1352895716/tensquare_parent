package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class LabelService {
    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public Label queryById(String labelId){
        return labelDao.findById(labelId).get();
    }

    public List<Label>getAll(){
        return labelDao.findAll();
    }

    public void addLabel(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    public void updLabel(Label label){
        labelDao.save(label);
    }

    public void delLabel(String labelId){
        labelDao.deleteById(labelId);
    }
    public List<Label> findSearch(Label label){
        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                /**
                 * root 跟对象，就是要把条件封装到哪个对象中，where 类名=label.getid
                 * criteriaQuery 封装查询关键字,比如groupby order by等【但是基本不用这个对象】
                 *
                 * criteriaBuilder 用来封装条件对象
                 * */
                //new 一个集合存放所有的条件
                ArrayList<Predicate> list = new ArrayList<>();
                if(label.getLabelname()!=null&&!"".equals(label.getLabelname())) {
                    Predicate labelname = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    //上句相当于 where labelname like '%哈哈%'
                    list.add(labelname);
                }
                if(label.getState()!=null&&!"".equals(label.getState())) {
                    Predicate labelname = criteriaBuilder.equal(root.get("state").as(String.class), label.getState() );

                    //上句相当于 where state = label.getState()
                    list.add(labelname);
                }
                Predicate[] parr=new Predicate[list.size()];//根据集合大小来创建数组  ，节省空间
                //把list转成数据
                list.toArray(parr);               //and()方法中不能放集合类型
                return criteriaBuilder.and(parr);// where labelname like '%哈哈%' and state='1'

            }
        });
    }

    public Page<Label> pageQuery(Label label, int page, int size) {
        //pageRequest间接实现了Pageable接口
        Pageable pageable= PageRequest.of(page-1,size);
        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                /**
                 * root 跟对象，就是要把条件封装到哪个对象中，where 类名=label.getid
                 * criteriaQuery 封装查询关键字,比如groupby order by等【但是基本不用这个对象】
                 *
                 * criteriaBuilder 用来封装条件对象
                 * */
                //new 一个集合存放所有的条件
                ArrayList<Predicate> list = new ArrayList<>();
                if(label.getLabelname()!=null&&!"".equals(label.getLabelname())) {
                    Predicate labelname = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    //上句相当于 where labelname like '%哈哈%'
                    list.add(labelname);
                }
                if(label.getState()!=null&&!"".equals(label.getState())) {
                    Predicate labelname = criteriaBuilder.equal(root.get("state").as(String.class), label.getState() );

                    list.add(labelname);
                }
                Predicate[] parr=new Predicate[list.size()];
                //把list转成数据
                list.toArray(parr);
                return criteriaBuilder.and(parr);// where labelname like '%哈哈%' and state='1'
            }
        },pageable);
    }

}


