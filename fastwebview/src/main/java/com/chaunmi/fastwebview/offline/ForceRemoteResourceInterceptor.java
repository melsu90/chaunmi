package com.chaunmi.fastwebview.offline;

import android.content.Context;

import com.chaunmi.fastwebview.config.CacheConfig;
import com.chaunmi.fastwebview.config.MimeTypeFilter;
import com.chaunmi.fastwebview.loader.OkHttpResourceLoader;
import com.chaunmi.fastwebview.loader.ResourceLoader;
import com.chaunmi.fastwebview.loader.SourceRequest;
import com.chaunmi.fastwebview.view.base.WebResource;

/**
 * Created by Ryan
 * at 2019/9/27
 */
public class ForceRemoteResourceInterceptor implements Destroyable, ResourceInterceptor {

    private ResourceLoader mResourceLoader;
    private MimeTypeFilter mMimeTypeFilter;

    ForceRemoteResourceInterceptor(Context context, CacheConfig cacheConfig) {
        mResourceLoader = new OkHttpResourceLoader(context);
        mMimeTypeFilter = cacheConfig != null ? cacheConfig.getFilter() : null;
    }

    @Override
    public WebResource load(Chain chain) {
        CacheRequest request = chain.getRequest();
        String mime = request.getMime();
        boolean cacheable = mMimeTypeFilter.isContains(mime);
        SourceRequest sourceRequest = new SourceRequest(request);
        sourceRequest.setCacheByOurself(cacheable);
        WebResource resource = mResourceLoader.getResource(sourceRequest);
        if (resource != null) {
            return resource;
        }
        return chain.process(request);
    }

    @Override
    public void destroy() {
        if (mMimeTypeFilter != null) {
            mMimeTypeFilter.clear();
        }
    }
}
