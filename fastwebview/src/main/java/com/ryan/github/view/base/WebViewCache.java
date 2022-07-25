package com.ryan.github.view.base;

import com.ryan.github.view.offline.Destroyable;

/**
 * Created by Ryan
 * 2018/2/7 下午5:06
 */
public interface WebViewCache extends FastOpenApi, Destroyable {

    Object getResource(Object request, int webViewCacheMode, String userAgent);

}
