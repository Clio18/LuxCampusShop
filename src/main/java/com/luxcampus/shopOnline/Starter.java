package com.luxcampus.shopOnline;

import com.luxcampus.shopOnline.dao.jdbc.ProductDAOImpl;
import com.luxcampus.shopOnline.service.ProductService;
import com.luxcampus.shopOnline.web.servlet.GetRequestServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Starter {
    public static void main(String[] args) throws Exception {
        // config

        // dao
        ProductDAOImpl productDAO = new ProductDAOImpl();

        // service
        ProductService productService = new ProductService(productDAO);

        // servlet
        GetRequestServlet getRequestServlet = new GetRequestServlet(productService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(getRequestServlet), "/");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}
