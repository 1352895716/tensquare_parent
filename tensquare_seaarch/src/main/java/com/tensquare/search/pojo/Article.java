package com.tensquare.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

//indexName 相当于数据库
//type 相当于表
//document 相当于行
@Document(indexName = "article",type = "article1")
public class Article implements Serializable {//对文章进行搜索
    //业务字段（以后要用到or要被搜索）

    @Id
    private String id;
    //analyzer:表示存储时候的划分方式，searchAnalyzer：代表取得时候的分词器划分方式
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",type = FieldType.text)
    private String title;
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",type = FieldType.text)
    private String content;
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
