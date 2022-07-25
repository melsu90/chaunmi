package com.ryan.github.view.x5;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.ryan.github.view.base.BaseCookieJarImpl;
import com.ryan.github.view.cookie.CookieInterceptor;
import com.ryan.github.view.cookie.FastCookieManager;
import com.tencent.smtt.sdk.CookieManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class X5CookieJarImpl extends BaseCookieJarImpl {

    public X5CookieJarImpl() {
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
