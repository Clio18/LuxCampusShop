package com.luxcampus.shopOnline.web.servlet;

import com.luxcampus.shopOnline.entity.Product;
import com.luxcampus.shopOnline.service.ProductService;
import com.luxcampus.shopOnline.web.util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
       double price = Double.parseDouble(req.getParameter("price"));
       Product product = Product.builder().name(name).price(price).date(LocalDateTime.now()).build();
       productService.save(product);
       resp.sendRedirect("/");
    }
}
