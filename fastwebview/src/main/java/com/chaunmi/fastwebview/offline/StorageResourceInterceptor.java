package com.chaunmi.fastwebview.offline;

import static java.net.HttpURLConnection.HTTP_OK;

import com.chaunmi.fastwebview.config.CacheConfig;
import com.chaunmi.fastwebview.utils.LogUtils;
import com.chaunmi.fastwebview.utils.StorageLoader;
import com.chaunmi.fastwebview.view.base.WebResource;

import java.io.File;
import java.io.InputStream;

/**
 * url路径和本地文件路径要对应，本地文件路径可以只有最后一两级路径，url路径要覆盖掉本地文件路径
 */
public class StorageResourceInterceptor implements Destroyable, ResourceInterceptor {
    private final File dir;

    StorageResourceInterceptor(CacheConfig cacheConfig) {
        dir = new File(cacheConfig.getOtherStorageDir());
    }

    @Override
    public void destroy() {

    }

    @Override
    public WebResource load(Chain chain) {
        CacheRequest request = chain.getRequest();
        String url = request.getUrl();
        InputStream inputStream = StorageLoader.getStorageFileStream(dir, url);
        if(inputStream != null) {
            WebResource webResource = new WebResource();
            webResource.setInputStream(inputStream);
            webResource.setResponseCode(HTTP_OK);
            webResource.setReasonPhrase(PhraseList.getPhrase(HTTP_OK));
            LogUtils.d(String.format("storage cache hit: %s", request.getUrl()));
            return webResource;
        }
        return chain.process(request);
    }
}