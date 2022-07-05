package com.ryan.github.view.offline;



import androidx.collection.LruCache;

import com.ryan.github.view.WebResource;
import com.ryan.github.view.config.CacheConfig;
import com.ryan.github.view.utils.LogUtils;


/**
 * Created by Ryan
 * on 2020/4/14
 *
 */
public class MemResourceInterceptor implements ResourceInterceptor, Destroyable {

    private static volatile MemResourceInterceptor sInstance;

    public static MemResourceInterceptor getInstance(CacheConfig cacheConfig) {
        if (sInstance == null) {
            synchronized (MemResourceInterceptor.class) {
                if (sInstance == null) {
                    sInstance = new MemResourceInterceptor(cacheConfig);
                }
            }
        }
        return sInstance;
    }



    private MemResourceInterceptor(CacheConfig cacheConfig) {
        int memorySize = cacheConfig.getMemCacheSize();
        if (memorySize > 0) {
            LruCacheManager.getInstance().init(memorySize);
        }
    }

    @Override
    public WebResource load(Chain chain) {
        CacheRequest request = chain.getRequest();
        WebResource resource = LruCacheManager.getInstance().get(request.getKey());
        if (checkResourceValid(resource)) {
            LogUtils.d(String.format("mem cache hit: %s", request.getUrl()));
            return resource;
        }
        resource = chain.process(request);
        if (checkResourceValid(resource) && resource.isCacheable() && resource.isCacheByOurselves()) {
            LruCacheManager.getInstance().put(request.getKey(), resource);
        }
        return resource;
    }

    private boolean checkResourceValid(WebResource resource) {
        return resource != null
                && resource.getOriginBytes() != null
                && resource.getOriginBytes().length >= 0
                && resource.getResponseHeaders() != null
                && !resource.getResponseHeaders().isEmpty();
    }

    public static boolean isMemInit() {
        return sInstance != null;
    }


    @Override
    public void destroy() {
//        if (mLruCache != null) {
//            mLruCache.evictAll();
//            mLruCache = null;
//        }
    }
}
