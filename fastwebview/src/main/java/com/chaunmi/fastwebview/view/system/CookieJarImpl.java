package com.chaunmi.fastwebview.view.system;

import android.webkit.CookieManager;

import com.chaunmi.fastwebview.view.base.BaseCookieJarImpl;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class CookieJarImpl extends BaseCookieJarImpl {


    public CookieJarImpl() {
        super();
    }

    @Override
    public String getCookie(HttpUrl url) {
        return CookieManager.getInstance().getCookie(url.host());
    }

    @Override
    public void setCookie(HttpUrl url, List<Cookie> cookies) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        for (Cookie cookie : cookies) {
            cookieManager.setCookie(url.toString(), cookie.toString());
        }
    }
}
