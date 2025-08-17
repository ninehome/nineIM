package com.watayouxiang.httpclient.prefernces;

import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.OkGo;
import com.watayouxiang.httpclient.TioHttpClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * author : TaoWang
 * date : 2020-02-04
 * desc : tio cookie 工具类
 */
public class CookieUtils {

    /**
     * 从本地存储获取tioCookie
     *
     * @return tioCookie
     */
    public static String getCookie() {
        // tio cookies
        List<Cookie> cookies = getCookies();
        // tio cookie name
        String sessionCookieName = HttpPreferences.getSessionCookieName();
        // find tio cookie value
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(sessionCookieName, cookie.name())) {
                return cookie.value();
            }
        }
        return null;
    }

    /**
     * 从本地存储获取tioCookies
     *
     * @return tioCookies
     */
    public static List<Cookie> getCookies() {
        // base url
        HttpUrl baseUrl = HttpUrl.parse(TioHttpClient.getBaseUrl());
        // http all cookies
        System.out.println("fighting:CookieUtils"+baseUrl);
        if (baseUrl != null){
            return OkGo.getInstance().getCookieJar().getCookieStore().getCookie(baseUrl);
        }
        return new ArrayList<Cookie>();

    }

    /**
     * 筛选出tioCookie
     *
     * @param cookies 待筛选的cookie集合
     * @return tioCookie
     */
    public static String getCookie(List<Cookie> cookies) {
        if (cookies == null || cookies.isEmpty()) return null;
        // tio cookie name
        String sessionCookieName = HttpPreferences.getSessionCookieName();
        // find tio cookie value
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(sessionCookieName, cookie.name())) {
                return cookie.value();
            }
        }
        return null;
    }

    /**
     * 移除tioCookie
     */
    public static void removeCookies() {
        // base url
        HttpUrl baseUrl = HttpUrl.parse(TioHttpClient.getBaseUrl());
        // remove all cookies
        OkGo.getInstance().getCookieJar().getCookieStore().removeCookie(baseUrl);
    }

}
