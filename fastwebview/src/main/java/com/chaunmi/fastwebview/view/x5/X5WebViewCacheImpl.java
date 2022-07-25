package com.chaunmi.fastwebview.view.x5;

import android.content.Context;

import com.chaunmi.fastwebview.view.base.BaseWebViewCacheImpl;
import com.chaunmi.fastwebview.offline.WebResourceResponseGenerator;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;

import java.util.Map;

public class X5WebViewCacheImpl extends BaseWebViewCacheImpl {

    public X5WebViewCacheImpl(Context context) {
        super(context);
    }

    @Override
    public WebResourceResponseGenerator getWebResourceResponseGenerator() {
        return new  X5WebResourceResponseGenerator();
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