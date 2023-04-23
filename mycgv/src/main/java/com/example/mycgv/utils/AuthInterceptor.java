package com.example.mycgv.utils;

import org.hibernate.Session;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        SessionVO svo = (SessionVO) session.getAttribute("svo");

        if(svo == null) {
            response.sendRedirect("http://localhost:9000/users/login?auth=fail");
            return false;
        }else {
            if(!svo.getId().equals("amdin")) {
                response.sendRedirect("http://localhost:9000/users/login?auth=fail");
                return false;
            }
        }
        return true;
    }
}
