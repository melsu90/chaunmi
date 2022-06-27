package com.ryan.github.view.offline;

import static java.net.HttpURLConnection.HTTP_OK;

import android.content.Context;

import com.ryan.github.view.WebResource;
import com.ryan.github.view.config.CacheConfig;
import com.ryan.github.view.utils.AssetsLoader;

import java.io.InputStream;

public class AssetResourceInterceptor implements Destroyable, ResourceInterceptor {
    private CacheConfig mCacheConfig;
    private Context mContext;

    AssetResourceInterceptor(Context context, CacheConfig cacheConfig) {
        this.mCacheConfig = cacheConfig;
        this.mContext = context;
        AssetsLoader.getInstance().init(mContext, mCacheConfig.getAssetsDir());
    }

    @Override
    public void destroy() {
        this.mContext = null;
    }

    @Override
    public WebResource load(Chain chain) {
        CacheRequest request = chain.getRequest();
        String url = request.getUrl();
        InputStream inputStream = AssetsLoader.getInstance().getResByUrl(mContext,url);
        if(inputStream != null) {
            WebResource webResource = new WebResource();
            webResource.setInputStream(inputStream);
            webResource.setResponseCode(HTTP_OK);
            webResource.setReasonPhrase(PhraseList.getPhrase(HTTP_OK));
            return webResource;
        }
        return chain.process(request);
    }
}
