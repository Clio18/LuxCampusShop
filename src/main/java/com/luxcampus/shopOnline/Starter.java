package com.luxcampus.shopOnline;

import com.luxcampus.shopOnline.dao.jdbc.ProductDAOImpl;
import com.luxcampus.shopOnline.dao.jdbc.UserDAOImpl;
import com.luxcampus.shopOnline.service.ProductService;
import com.luxcampus.shopOnline.service.SecurityService;
import com.luxcampus.shopOnline.service.UserService;
import com.luxcampus.shopOnline.web.filter.SecurityFilter;
import com.luxcampus.shopOnline.web.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import javax.servlet.DispatcherType;
import java.util.*;

//@Slf4j
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
        GetRequestServlet getRequestServlet = new GetRequestServlet(productService);
        AddRequestServlet addRequestServlet = new AddRequestServlet(productService);
        DeleteRequestServlet deleteRequestServlet = new DeleteRequestServlet(productService);
        UpdateRequestServlet updateRequestServlet = new UpdateRequestServlet(productService);
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

        //connection in prod requires usage of environment variables
        Server server;
        String port = System.getenv("PORT");
        if(port!=null){
           server = new Server(Integer.parseInt(port));
        }else {
            server = new Server(8088);
        }

        server.setHandler(context);

        printSystemVariables();
        System.out.println();
        printEnvVariables();

        server.start();


    }

    //shows all system variables
    private static void printSystemVariables(){
        System.out.println("=====SYSTEM VARIABLES START=====");
        Properties properties = System.getProperties();
        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
           //log.info(entry.getKey() + " = " + entry.getValue());
            System.out.println((entry.getKey() + " = " + entry.getValue()));
        }
        System.out.println("=====SYSTEM VARIABLES STOP=====");
    }

    //shows all environment variables
    private static void printEnvVariables(){
        System.out.println("=====ENV VARIABLES START=====");
        Map<String, String> getenv = System.getenv();
        Set<Map.Entry<String, String>> entries = getenv.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println((entry.getKey() + " = " + entry.getValue()));
        }
        System.out.println("====ENV VARIABLES STOP=====");
    }
}
