package com.lagou.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object userSession = session.getAttribute("user_session");
        if (userSession != null){
            return true;
        }else{
//            request.getRequestDispatcher("/toLogin").forward(request,response);
            response.sendRedirect("toLogin");
            return false;
        }

    }
}
