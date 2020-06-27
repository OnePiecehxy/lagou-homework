package com.lagou.respository;

import com.lagou.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRespository extends JpaRepository<Article,Integer> {
}
