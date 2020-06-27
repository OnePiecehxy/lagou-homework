package com.lagou.service;

import com.lagou.pojo.Article;
import com.lagou.respository.ArticleRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRespository articleRespository;
    public Page<Article> getArticleList(int pageNum, int pageSize){
        Sort created = Sort.by(Sort.Direction.DESC, "created");
        Pageable pageable = PageRequest.of(pageNum,pageSize,created);
        Page<Article> articles = articleRespository.findAll(pageable);
        return articles;
    }
}
