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
import java.util.List;
import java.util.Map;

public class DeleteRequestServlet extends HttpServlet {
    private ProductService productService;
    private List<String> userTokens;
    private SecurityService securityService;

    public DeleteRequestServlet(ProductService productService, List<String> userTokens, SecurityService securityService) {
        this.productService = productService;
        this.userTokens = userTokens;
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("deleteProduct.html");
        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (securityService.isAuth(req, userTokens)) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                System.out.println(id);
                productService.delete(id);
                resp.sendRedirect("/products");
            } catch (Exception e) {
                String errorMessage = "Your product has not been deleted! Please try again";
                PageGenerator pageGenerator = PageGenerator.instance();
                Map<String, Object> parameters = Map.of("errorMessage", errorMessage);
                String page = pageGenerator.getPage("deleteProduct.html", parameters);
                resp.getWriter().write(page);
            }
        } else {
            resp.sendRedirect("/login");
        }
    }
}
