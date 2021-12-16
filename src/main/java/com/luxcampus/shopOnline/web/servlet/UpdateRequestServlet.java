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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateRequestServlet extends HttpServlet {
    private ProductService productService;
    private List<String> userTokens;
    private SecurityService securityService;

    public UpdateRequestServlet(ProductService productService, List<String> userTokens, SecurityService securityService) {
        this.productService = productService;
        this.userTokens = userTokens;
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productService.getById(id);
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("product", product);
        String page = pageGenerator.getPage("updateProduct.html", parameters);
        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            try {
                System.out.println("HERE");

                int id = Integer.parseInt(req.getParameter("id"));
                String name = req.getParameter("name");
                double price = Double.parseDouble(req.getParameter("price"));
                Product product = Product.builder().id(id).name(name).price(price).build();
                productService.update(product);
                resp.sendRedirect("/products");
            } catch (Exception e) {
                String errorMessage = "Your product has not been updated! Please try again";
                PageGenerator pageGenerator = PageGenerator.instance();
                Map<String, Object> parameters = Map.of("errorMessage", errorMessage);
                String page = pageGenerator.getPage("updateProduct.html", parameters);
                resp.getWriter().write(page);
            }
    }
}
