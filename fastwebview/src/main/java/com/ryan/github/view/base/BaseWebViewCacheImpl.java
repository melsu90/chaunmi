package com.ryan.github.view.base;

import android.content.Context;

import com.ryan.github.view.config.CacheConfig;
import com.ryan.github.view.config.FastCacheMode;
import com.ryan.github.view.offline.CacheRequest;
import com.ryan.github.view.offline.OfflineServer;
import com.ryan.github.view.offline.OfflineServerImpl;
import com.ryan.github.view.offline.ResourceInterceptor;
import com.ryan.github.view.offline.WebResourceResponseGenerator;
import com.ryan.github.view.utils.LogUtils;
import com.ryan.github.view.utils.MimeTypeMapUtils;

import java.util.Map;

abstract public class BaseWebViewCacheImpl implements WebViewCache{
    private FastCacheMode mFastCacheMode;
    private CacheConfig mCacheConfig;
    private OfflineServer mOfflineServer;
    private Context mContext;

    public BaseWebViewCacheImpl(Context context) {
        mContext = context;
    }

    abstract public String getUrl(Object resourceRequest);
    abstract public Map<String, String> getRequestHeaders(Object resourceRequest);
    abstract public WebResourceResponseGenerator getWebResourceResponseGenerator();

    @Override
    public Object getResource(Object webResourceRequest, int cacheMode, String userAgent) {
        if (mFastCacheMode == FastCacheMode.DEFAULT) {
            throw new IllegalStateException("an error occurred.");
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            String url = getUrl(webResourceRequest);
            String extension = MimeTypeMapUtils.getFileExtensionFromUrl(url);
            String mimeType = MimeTypeMapUtils.getMimeTypeFromExtension(extension);
            CacheRequest cacheRequest = new CacheRequest();
            cacheRequest.setUrl(url);
            cacheRequest.setMime(mimeType);
            cacheRequest.setForceMode(mFastCacheMode == FastCacheMode.FORCE);
            cacheRequest.setUserAgent(userAgent);
            cacheRequest.setWebViewCacheMode(cacheMode);
            Map<String, String> headers = getRequestHeaders(webResourceRequest);
            cacheRequest.setHeaders(headers);
            return  getOfflineServer().get(cacheRequest);
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
            mOfflineServer = new OfflineServerImpl(mContext, getCacheConfig(), getWebResourceResponseGenerator());
        }
        return mOfflineServer;
    }

    private CacheConfig getCacheConfig() {
        return mCacheConfig != null ? mCacheConfig : generateDefaultCacheConfig();
    }

    private CacheConfig generateDefaultCacheConfig() {
        LogUtils.e("generateDefaultCacheConfig CacheConfig builder ");
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
        mContext = null;
        LogUtils.e(" WebViewCache destroy ");
    }
}
