package com.luxcampus.shopOnline.web.servlet;

import com.luxcampus.shopOnline.entity.User;
import com.luxcampus.shopOnline.service.SecurityService;
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

public class RegistrationServlet extends HttpServlet {
    private UserService userService;
    private SecurityService securityService;

    public RegistrationServlet(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("registration.html");
        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String name = req.getParameter("name");
            String lastName = req.getParameter("last_Name");
            String password = req.getParameter("password");
            String hashPassword = securityService.getHash(password);
            String email = req.getParameter("email");
            User user = User.builder()
                    .name(name)
                    .lastName(lastName)
                    .email(email)
                    //should be hashed
                    .password(hashPassword)
                    .build();
            userService.save(user);
            resp.sendRedirect("/login");
        } catch (Exception e) {
            String errorMessage = "You have not been registered! Please try again";
            PageGenerator pageGenerator = PageGenerator.instance();
            Map<String, Object> parameters = Map.of("errorMessage", errorMessage);
            String page = pageGenerator.getPage("registration.html", parameters);
            resp.getWriter().write(page);
        }
    }
}
