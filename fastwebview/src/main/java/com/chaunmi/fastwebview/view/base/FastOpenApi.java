package com.chaunmi.fastwebview.view.base;

import com.chaunmi.fastwebview.config.CacheConfig;
import com.chaunmi.fastwebview.config.FastCacheMode;
import com.chaunmi.fastwebview.offline.ResourceInterceptor;

/**
 * Created by Ryan
 * at 2019/11/1
 */
public interface FastOpenApi {

    void setCacheMode(FastCacheMode mode, CacheConfig cacheConfig);

    void addResourceInterceptor(ResourceInterceptor interceptor);
}
