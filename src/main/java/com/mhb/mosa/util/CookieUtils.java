package com.mhb.mosa.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @date: 2021/6/10 11:02
 */

public class CookieUtils {

    /**
     * 根据cookie名称从cookie中获取对应的值,存在返回对应的值，不存在返回""
     *
     * @param name    cookie名称
     * @param request request
     * @return java.lang.String
     */
    public static String getCookieValeByName(String name, HttpServletRequest request) {
        String value = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(name, cookie.getName())) {
                    value = cookie.getValue();
                    break;
                }
            }
        }
        return value;
    }

    /**
     * 写入cookies
     *
     * @param key
     * @param value
     * @param response
     */
    public void addCookies(String key, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, value);
        cookie.setDomain("");
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

}
