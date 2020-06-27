package com.example.module4homework;

import com.lagou.pojo.Article;
import com.lagou.respository.ArticleRespository;
import com.lagou.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class Module4HomeworkApplicationTests {

    @Test
    void contextLoads() {

        ArticleService articleService = new ArticleService();
        Page<Article> articleList = articleService.getArticleList(0, 2);
        for (Article article : articleList) {

            System.out.println(article.toString());
        }
    }

}
