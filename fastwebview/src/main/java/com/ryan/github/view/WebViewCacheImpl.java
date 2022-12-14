package com.ryan.github.view;

import android.content.Context;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;

import com.ryan.github.view.config.CacheConfig;
import com.ryan.github.view.config.FastCacheMode;
import com.ryan.github.view.offline.CacheRequest;
import com.ryan.github.view.offline.OfflineServer;
import com.ryan.github.view.offline.OfflineServerImpl;
import com.ryan.github.view.offline.ResourceInterceptor;
import com.ryan.github.view.utils.MimeTypeMapUtils;

import java.io.File;
import java.util.Map;

/**
 * Created by Ryan
 * 2018/2/7 下午5:07
 */
public class WebViewCacheImpl implements WebViewCache {

    private FastCacheMode mFastCacheMode;
    private CacheConfig mCacheConfig;
    private OfflineServer mOfflineServer;
    private Context mContext;

    WebViewCacheImpl(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public WebResourceResponse getResource(WebResourceRequest webResourceRequest, int cacheMode, String userAgent) {
        if (mFastCacheMode == FastCacheMode.DEFAULT) {
            throw new IllegalStateException("an error occurred.");
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            String url = webResourceRequest.getUrl().toString();
            String extension = MimeTypeMapUtils.getFileExtensionFromUrl(url);
            String mimeType = MimeTypeMapUtils.getMimeTypeFromExtension(extension);
            CacheRequest cacheRequest = new CacheRequest();
            cacheRequest.setUrl(url);
            cacheRequest.setMime(mimeType);
            cacheRequest.setForceMode(mFastCacheMode == FastCacheMode.FORCE);
            cacheRequest.setUserAgent(userAgent);
            cacheRequest.setWebViewCacheMode(cacheMode);
            Map<String, String> headers = webResourceRequest.getRequestHeaders();
            cacheRequest.setHeaders(headers);
            return getOfflineServer().get(cacheRequest);
        }
        throw new IllegalStateException("an error occurred.");
    }

    @Override
    public void setCacheMode(FastCacheMode mode, CacheConfig cacheConfig) {
        mFastCacheMode = mode;
        mCacheConfig = cacheConfig;
    }

    @Override
    public void addResourceInterceptor(ResourceInterceptor interceptor) {
        getOfflineServer().addResourceInterceptor(interceptor);
    }

    private synchronized OfflineServer getOfflineServer() {
        if (mOfflineServer == null) {
            mOfflineServer = new OfflineServerImpl(mContext, getCacheConfig());
        }
        return mOfflineServer;
    }

    private CacheConfig getCacheConfig() {
        if (mCacheConfig == null) {
            mCacheConfig = generateDefaultCacheConfig();
        }
        return mCacheConfig;
    }

    private CacheConfig generateDefaultCacheConfig() {
        return new CacheConfig.Builder(mContext).build();
    }

    @Override
    public void destroy() {
        if (mOfflineServer != null) {
            mOfflineServer.destroy();
        }
        // help gc
        mCacheConfig = null;
        mOfflineServer = null;
    }
}