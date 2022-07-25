package com.chaunmi.fastwebview.view.system;


import android.content.Context;
import android.webkit.WebResourceRequest;


import com.chaunmi.fastwebview.offline.WebResourceResponseGenerator;
import com.chaunmi.fastwebview.view.base.BaseWebViewCacheImpl;
import java.util.Map;

public class WebViewCacheImpl extends BaseWebViewCacheImpl {

    public WebViewCacheImpl(Context context) {
        super(context);
    }

    @Override
    public WebResourceResponseGenerator getWebResourceResponseGenerator() {
        return new SystemWebResourceResponseGenerator();
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