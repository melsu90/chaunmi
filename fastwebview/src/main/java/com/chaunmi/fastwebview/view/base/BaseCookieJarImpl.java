package com.chaunmi.fastwebview.view.base;

import android.text.TextUtils;

import androidx.annotation.NonNull;


import com.chaunmi.fastwebview.cookie.CookieInterceptor;
import com.chaunmi.fastwebview.cookie.FastCookieManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

abstract public class BaseCookieJarImpl implements CookieJar {

    private FastCookieManager mCookieManager;

    public BaseCookieJarImpl() {
        mCookieManager = FastCookieManager.getInstance();
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        List<CookieInterceptor> interceptors = mCookieManager.getRequestCookieInterceptors();
        if (interceptors != null && !interceptors.isEmpty()) {
            for (CookieInterceptor interceptor : interceptors) {
                cookies = interceptor.newCookies(url, cookies);
            }
        }
        setCookie(url, cookies);
    }

    abstract public String getCookie(HttpUrl url);
    abstract public void setCookie(HttpUrl url, List<Cookie> cookies);

    @NonNull
    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = new ArrayList<>();
        String cookieFullStr = getCookie(url);
        if (!TextUtils.isEmpty(cookieFullStr)) {
            String[] cookieArr = cookieFullStr.split(";");
            for (String cookieStr : cookieArr) {
                Cookie cookie = Cookie.parse(url, cookieStr);
                if (cookie != null) {
                    cookies.add(cookie);
                }
            }
        }
        List<CookieInterceptor> interceptors = mCookieManager.getResponseCookieInterceptors();
        if (interceptors != null && !interceptors.isEmpty()) {
            for (CookieInterceptor interceptor : interceptors) {
                cookies = interceptor.newCookies(url, cookies);
            }
        }
        return cookies;
    }
}
