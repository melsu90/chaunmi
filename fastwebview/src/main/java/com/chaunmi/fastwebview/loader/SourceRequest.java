package com.chaunmi.fastwebview.loader;

import com.chaunmi.fastwebview.offline.CacheRequest;

import java.util.Map;

/**
 * Created by Ryan
 * at 2019/9/27
 */
public class SourceRequest {

    private String url;
    //是否由okhttp缓存
    private boolean isCacheByOkHttp;
    //由自己缓存
    private boolean isCacheByOurself;
    private Map<String, String> headers;
    private String userAgent;
    private int webViewCache;

    public SourceRequest(CacheRequest request){
        this.url = request.getUrl();
        this.headers = request.getHeaders();
        this.userAgent = request.getUserAgent();
        this.webViewCache = request.getWebViewCacheMode();
    }

    public void setCacheByOurself(boolean cacheByOurself) {
        this.isCacheByOurself = cacheByOurself;
    }

    public boolean isCacheByOurself() {
        return this.isCacheByOurself;
    }

    public String getUrl() {
        return url;
    }

    public void setCacheByOkHttp(boolean isCacheByOkHttp) {
        this.isCacheByOkHttp = isCacheByOkHttp;
    }

    public boolean isCacheableByOkHttp() {
        return isCacheByOkHttp;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getWebViewCache() {
        return webViewCache;
    }

    public void setWebViewCache(int webViewCache) {
        this.webViewCache = webViewCache;
    }
}
