package com.lagou.controller;

import com.lagou.pojo.Article;
import com.lagou.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;


    @RequestMapping("/")
    public String index() {
        return "redirect:/list";
    }


    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value = "pageNum",defaultValue = "0") int pageNum){
        Page<Article> articleList = articleService.getArticleList(pageNum, 2);
        model.addAttribute("articles",articleList);
        return "client/index";
    }

}
