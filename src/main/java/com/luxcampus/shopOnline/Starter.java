package com.luxcampus.shopOnline;

import com.luxcampus.shopOnline.dao.jdbc.ProductDAOImpl;
import com.luxcampus.shopOnline.dao.jdbc.UserDAOImpl;
import com.luxcampus.shopOnline.service.ProductService;
import com.luxcampus.shopOnline.service.SecurityService;
import com.luxcampus.shopOnline.service.UserService;
import com.luxcampus.shopOnline.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.ArrayList;
import java.util.List;

public class Starter {
    public static void main(String[] args) throws Exception {
        // config

        // dao
        ProductDAOImpl productDAO = new ProductDAOImpl();
        UserDAOImpl userDAO = new UserDAOImpl();

        // service
        ProductService productService = new ProductService(productDAO);
        UserService userService = new UserService(userDAO);

        //security
        SecurityService securityService = new SecurityService();

        //list of user tokens for auth
        List<String> userTokens = new ArrayList<>();

        // servlet
        GetRequestServlet getRequestServlet = new GetRequestServlet(productService, userTokens, securityService);
        AddRequestServlet addRequestServlet = new AddRequestServlet(productService, userTokens, securityService);
        DeleteRequestServlet deleteRequestServlet = new DeleteRequestServlet(productService, userTokens, securityService);
        UpdateRequestServlet updateRequestServlet = new UpdateRequestServlet(productService, userTokens, securityService);
        //login servlet
        LoginServlet loginServlet = new LoginServlet(userService, userTokens);
        RegistrationServlet registrationServlet = new RegistrationServlet(userService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(getRequestServlet), "/products");
        context.addServlet(new ServletHolder(addRequestServlet), "/products/add");
        context.addServlet(new ServletHolder(deleteRequestServlet), "/products/delete");
        context.addServlet(new ServletHolder(updateRequestServlet), "/products/update");

        context.addServlet(new ServletHolder(registrationServlet), "/registration");
        context.addServlet(new ServletHolder(loginServlet), "/login");

        Server server = new Server(8088);
        server.setHandler(context);

        server.start();
    }
}
