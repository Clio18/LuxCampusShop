package com.luxcampus.shopOnline;

import com.luxcampus.shopOnline.dao.jdbc.ProductDAOImpl;
import com.luxcampus.shopOnline.service.ProductService;
import com.luxcampus.shopOnline.web.servlet.AddRequestServlet;
import com.luxcampus.shopOnline.web.servlet.DeleteRequestServlet;
import com.luxcampus.shopOnline.web.servlet.GetRequestServlet;
import com.luxcampus.shopOnline.web.servlet.UpdateRequestServlet;
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
        AddRequestServlet addRequestServlet = new AddRequestServlet(productService);
        DeleteRequestServlet deleteRequestServlet = new DeleteRequestServlet(productService);
        UpdateRequestServlet updateRequestServlet = new UpdateRequestServlet(productService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(getRequestServlet), "/products");
        context.addServlet(new ServletHolder(addRequestServlet), "/products/add");
        context.addServlet(new ServletHolder(deleteRequestServlet), "/products/delete");
        context.addServlet(new ServletHolder(updateRequestServlet), "/products/update");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}
