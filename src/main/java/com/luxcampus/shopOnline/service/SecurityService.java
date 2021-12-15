package com.luxcampus.shopOnline.service;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SecurityService {
    private List<String> userTokens;

    public SecurityService(List<String> userTokens) {
        this.userTokens = userTokens;
    }

    public boolean isAuth(HttpServletRequest req) {
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

    public void logOut() {
        userTokens.clear();
    }
}
