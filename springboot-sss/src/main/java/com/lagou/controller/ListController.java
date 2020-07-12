package com.lagou.controller;

import com.lagou.dao.ResumeDao;
import com.lagou.pojo.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

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
    @RequestMapping("/delete")
    public String delete(Integer id){
        resumeDao.deleteById(id);
        return "redirect:list";
    }
    @RequestMapping("/toAdd")
    public String toAddPage(){
        return "add";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String addResume(String address, String name, String phone){
        Resume resume = new Resume();
        resume.setAddress(address);
        resume.setName(name);
        resume.setPhone(phone);
        resumeDao.save(resume);
        return "redirect:list";
    }

    @RequestMapping("/toUpdate")
    public ModelAndView toUpdatePage(Integer id, String address, String name, String phone){
        System.out.println("=========================");
        Resume resume = new Resume();
        resume.setId(id);
        resume.setAddress(address);
        resume.setName(name);
        resume.setPhone(phone);
        System.out.println(resume.toString());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("update");
        modelAndView.getModel().put("resume", resume);
        return modelAndView;
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String updateResume(Integer id, String address, String name, String phone){

        Resume resume = new Resume();
        resume.setId(id);
        resume.setAddress(address);
        resume.setName(name);
        resume.setPhone(phone);
        System.out.println("更新sql=" + resume.toString());
        resumeDao.save(resume);
//        resumeDao.saveAndFlush(resume);
        return "redirect:list";
    }
}
