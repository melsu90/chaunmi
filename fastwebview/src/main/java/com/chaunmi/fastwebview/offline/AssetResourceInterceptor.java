package com.chaunmi.fastwebview.offline;

import static java.net.HttpURLConnection.HTTP_OK;

import android.content.Context;

import com.chaunmi.fastwebview.config.CacheConfig;
import com.chaunmi.fastwebview.utils.AssetsLoader;
import com.chaunmi.fastwebview.utils.LogUtils;
import com.chaunmi.fastwebview.view.base.WebResource;

import java.io.InputStream;

/**
 * 加载asset资源
 */
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
            LogUtils.d(String.format("asset cache hit: %s", request.getUrl()));
            return webResource;
        }
        return chain.process(request);
    }
}
