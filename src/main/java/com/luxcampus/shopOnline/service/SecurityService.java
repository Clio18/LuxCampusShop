package com.luxcampus.shopOnline.service;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SecurityService {
    public boolean isAuth(HttpServletRequest req, List<String> userTokens) {
        boolean isAuth = false;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (userTokens.contains(cookie.getValue())) {
                        isAuth = true;
                    }
                }
            }
        }
        return isAuth;
    }

    public String getHash(String password) {
        String md5Hex = DigestUtils.md5Hex(password);
        return md5Hex;
    }
}
