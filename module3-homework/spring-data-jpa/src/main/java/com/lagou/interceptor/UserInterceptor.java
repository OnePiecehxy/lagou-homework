package com.lagou.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpSession;

public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object userSession = session.getAttribute("user_session");

        if (userSession != null){
            return true;
        }else {
            request.getRequestDispatcher("/index").forward(request,response);
            return false;
        }

    }
}
