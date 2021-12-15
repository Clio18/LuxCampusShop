package com.luxcampus.shopOnline;

import com.luxcampus.shopOnline.dao.jdbc.ProductDAOImpl;
import com.luxcampus.shopOnline.dao.jdbc.UserDAOImpl;
import com.luxcampus.shopOnline.service.ProductService;
import com.luxcampus.shopOnline.service.SecurityService;
import com.luxcampus.shopOnline.service.UserService;
import com.luxcampus.shopOnline.web.filter.SecurityFilter;
import com.luxcampus.shopOnline.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class Starter {
    public static void main(String[] args) throws Exception {
        // config

        //list of user tokens for auth
        List<String> userTokens = Collections.synchronizedList(new ArrayList<>());

        // dao
        ProductDAOImpl productDAO = new ProductDAOImpl();
        UserDAOImpl userDAO = new UserDAOImpl();

        // service
        ProductService productService = new ProductService(productDAO);
        UserService userService = new UserService(userDAO);

        //security
        SecurityService securityService = new SecurityService(userTokens);

        // servlet
        GetRequestServlet getRequestServlet = new GetRequestServlet(productService, userTokens, securityService);
        AddRequestServlet addRequestServlet = new AddRequestServlet(productService, userTokens, securityService);
        DeleteRequestServlet deleteRequestServlet = new DeleteRequestServlet(productService, userTokens, securityService);
        UpdateRequestServlet updateRequestServlet = new UpdateRequestServlet(productService, userTokens, securityService);
        //login servlet
        LoginServlet loginServlet = new LoginServlet(userService, userTokens, securityService);
        RegistrationServlet registrationServlet = new RegistrationServlet(userService, securityService);
        //logout
        LogOutServlet logOutServlet = new LogOutServlet(securityService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(getRequestServlet), "/products");
        context.addServlet(new ServletHolder(addRequestServlet), "/products/add");
        context.addServlet(new ServletHolder(deleteRequestServlet), "/products/delete");
        context.addServlet(new ServletHolder(updateRequestServlet), "/products/update");

        context.addServlet(new ServletHolder(registrationServlet), "/registration");
        context.addServlet(new ServletHolder(loginServlet), "/login");
        context.addServlet(new ServletHolder(logOutServlet), "/logout");

        //filter
        context.addFilter(new FilterHolder(new SecurityFilter(securityService)), "/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8088);
        server.setHandler(context);

        server.start();
    }
}
