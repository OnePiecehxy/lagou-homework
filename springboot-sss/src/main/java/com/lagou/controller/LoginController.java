package com.lagou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username, String password) throws IOException {
        if ("admin".equals(username) && "admin".equals(password)){
            session.setAttribute("user_session",username);
            return "redirect:list";
        }else{
            return "redirect:toLogin";
        }
    }

    @RequestMapping("/toLogin")
    public String index(String username, String password){
        return "login";
    }
}
