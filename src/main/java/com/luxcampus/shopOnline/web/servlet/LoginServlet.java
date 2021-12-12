package com.luxcampus.shopOnline.web.servlet;

import com.luxcampus.shopOnline.entity.Product;
import com.luxcampus.shopOnline.entity.User;
import com.luxcampus.shopOnline.service.UserService;
import com.luxcampus.shopOnline.web.util.PageGenerator;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    private UserService userService;
    private List<String> userTokens;

    public LoginServlet(UserService userService, List<String> userTokens) {
        this.userService = userService;
        this.userTokens = userTokens;
    }

    //return login page
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("login.html");
        resp.getWriter().write(page);
    }

    //auth
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String password = req.getParameter("password");
            String email = req.getParameter("email");
            User user = User.builder().email(email).password(password).build();
            if (userService.isNew(user)) {
                resp.sendRedirect("/registration");
            } else {
                String userToken = UUID.randomUUID().toString();
                userTokens.add(userToken);
                Cookie cookie = new Cookie("user-token", userToken);
                resp.addCookie(cookie);
                resp.sendRedirect("/products");
            }
            System.out.println("Email " + email + ", password " + password);
        } catch (Exception e) {
            String errorMessage = "You have not been logged! Please try again";
            PageGenerator pageGenerator = PageGenerator.instance();
            Map<String, Object> parameters = Map.of("errorMessage", errorMessage);
            String page = pageGenerator.getPage("login.html", parameters);
            resp.getWriter().write(page);
        }
    }
}
