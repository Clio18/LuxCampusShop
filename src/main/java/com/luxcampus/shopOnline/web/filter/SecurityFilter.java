package com.luxcampus.shopOnline.web.filter;

import com.luxcampus.shopOnline.service.SecurityService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SecurityFilter implements Filter {
    private SecurityService securityService;
    //list of allowed paths for filter
    private List<String> allowedPath = List.of("/login", "/registration");

    public SecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        for (String allowed : allowedPath) {
            if (httpServletRequest.getRequestURI().equals(allowed)){
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        if (securityService.isAuth(httpServletRequest)){
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            httpServletResponse.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {

    }
}
