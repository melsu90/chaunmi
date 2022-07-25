package com.ryan.github.view;


import android.content.Context;
import android.webkit.WebResourceRequest;

import com.ryan.github.view.base.BaseWebViewCacheImpl;
import com.ryan.github.view.offline.DefaultWebResponseGenerator;
import com.ryan.github.view.offline.WebResourceResponseGenerator;

import java.util.Map;

public class WebViewCacheImpl extends BaseWebViewCacheImpl {

    public WebViewCacheImpl(Context context) {
        super(context);
    }

    @Override
    public WebResourceResponseGenerator getWebResourceResponseGenerator() {
        return new DefaultWebResponseGenerator();
    }

    @Override
    public String getUrl(Object webResourceRequest) {
        return ((WebResourceRequest) webResourceRequest).getUrl().toString();
    }

    @Override
    public Map<String, String> getRequestHeaders(Object webResourceRequest) {
        return ((WebResourceRequest) webResourceRequest).getRequestHeaders();
    }
}