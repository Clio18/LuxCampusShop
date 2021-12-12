package com.luxcampus.shopOnline.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SecurityService {
    public boolean isAuth(HttpServletRequest req, List<String> userTokens) {
        boolean isAuth = false;
        Cookie[] cookies = req.getCookies();
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                for (String userToken : userTokens) {
                    if (cookie.getValue().equals(userToken)){
                        isAuth = true;
                    }
                }
            }
        }
        return isAuth;
    }
}
