package com.chaunmi.fastwebview.offline;

/**
 * Created by Ryan
 * at 2019/9/27
 */
public interface OfflineServer {

    Object get(CacheRequest request);

    void addResourceInterceptor(ResourceInterceptor interceptor);

    void destroy();
}
