package com.chaunmi.fastwebview.offline;

import android.content.Context;

import com.chaunmi.fastwebview.loader.OkHttpResourceLoader;
import com.chaunmi.fastwebview.loader.ResourceLoader;
import com.chaunmi.fastwebview.loader.SourceRequest;
import com.chaunmi.fastwebview.view.base.WebResource;

/**
 * Created by Ryan
 * at 2019/9/27
 */
public class DefaultRemoteResourceInterceptor implements ResourceInterceptor {

    private ResourceLoader mResourceLoader;

    DefaultRemoteResourceInterceptor(Context context) {
        mResourceLoader = new OkHttpResourceLoader(context);
    }

    @Override
    public WebResource load(Chain chain) {
        CacheRequest request = chain.getRequest();
        SourceRequest sourceRequest = new SourceRequest(request);
        sourceRequest.setCacheByOkHttp(true);
        WebResource resource = mResourceLoader.getResource(sourceRequest);
        if (resource != null) {
            return resource;
        }
        return chain.process(request);
    }
}
