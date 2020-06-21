package com.lagou.controller;

import com.lagou.dao.ResumeDao;
import com.lagou.pojo.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ListController {
    @Autowired
    private ResumeDao resumeDao;
    @RequestMapping("/list")
    public ModelAndView query(){
        List<Resume> resumes = resumeDao.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("list");
        modelAndView.getModel().put("lists", resumes);

        return modelAndView;
    }

    @RequestMapping("/result")
    public ModelAndView result(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");
        modelAndView.addObject("result", "1234567");
//        modelAndView.getModel().put("result", "1234567");
        return modelAndView;
    }

    @RequestMapping("/list1")
    public void query1(){
        List<Resume> resumes = resumeDao.findAll();
//        Optional<Resume> optional = resumeDao.findById(1);
//        Resume resume = optional.get();
        for (Resume resume : resumes) {
            System.out.println(resume.toString());
        }

    }
}
