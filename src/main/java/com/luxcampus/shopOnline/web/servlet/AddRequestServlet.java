package com.luxcampus.shopOnline.web.servlet;

import com.luxcampus.shopOnline.entity.Product;
import com.luxcampus.shopOnline.service.ProductService;
import com.luxcampus.shopOnline.service.SecurityService;
import com.luxcampus.shopOnline.web.util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddRequestServlet extends HttpServlet {
    private ProductService productService;

    public AddRequestServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("addProduct.html");
        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
           try {
               String name = req.getParameter("name");
               double price = Double.parseDouble(req.getParameter("price"));
               Product product = Product.builder().name(name).price(price).build();
               productService.save(product);
               resp.sendRedirect("/products");
           } catch (Exception e) {
               String errorMessage = "Your product has not been added! Please try again";
               PageGenerator pageGenerator = PageGenerator.instance();
               Map<String, Object> parameters = Map.of("errorMessage", errorMessage);
               String page = pageGenerator.getPage("addProduct.html", parameters);
               resp.getWriter().write(page);
           }
    }
}
