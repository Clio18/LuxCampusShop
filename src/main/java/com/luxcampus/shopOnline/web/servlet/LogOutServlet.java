package com.luxcampus.shopOnline.web.servlet;

import com.luxcampus.shopOnline.service.SecurityService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOutServlet extends HttpServlet {
    private SecurityService securityService;

    public LogOutServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("user-token")){
                System.out.println(cookie.getValue());
                securityService.logOut(cookie);
            }
        }
        resp.sendRedirect("/login");
    }
}
