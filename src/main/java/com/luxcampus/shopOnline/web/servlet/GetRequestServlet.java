package com.luxcampus.shopOnline.web.servlet;

import com.luxcampus.shopOnline.entity.Product;
import com.luxcampus.shopOnline.service.ProductService;
import com.luxcampus.shopOnline.service.SecurityService;
import com.luxcampus.shopOnline.web.util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetRequestServlet extends HttpServlet {
    private ProductService productService;

    public GetRequestServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            List<Product> products = productService.get();
            PageGenerator pageGenerator = PageGenerator.instance();
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("products", products);

            String page = pageGenerator.getPage("products.html", parameters);
            resp.getWriter().write(page);
    }
}
