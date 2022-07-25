package com.chaunmi.fastwebview.utils;

import android.content.Context;
import android.content.MutableContextWrapper;
import androidx.core.util.Pools;

import com.chaunmi.fastwebview.utils.LogUtils;
import com.chaunmi.fastwebview.view.system.FastWebView;
import com.chaunmi.fastwebview.view.x5.FastX5WebView;

/**
 * A simple webview instance pool.
 * Reduce webview initialization time about 100ms.
 * my test env: vivo-x23, android api: 8.1
 * <p>
 * Created by Ryan
 * at 2019/11/4
 */
public class FastWebViewPool {

    private static final int MAX_POOL_SIZE = 2;
    private static final Pools.Pool<FastWebView> sPool = new Pools.SynchronizedPool<>(MAX_POOL_SIZE);
    private static final Pools.Pool<FastX5WebView> sPoolX5 = new Pools.SynchronizedPool<>(MAX_POOL_SIZE);


    public static void prepareX5(Context context) {
        releaseX5(acquireX5(context.getApplicationContext()));
    }

    public static void prepare(Context context) {
        release(acquire(context.getApplicationContext()));
    }

    public static FastX5WebView acquireX5(Context context) {
        FastX5WebView webView = sPoolX5.acquire();
        if (webView == null) {
            MutableContextWrapper wrapper = new MutableContextWrapper(context);
            webView = new FastX5WebView(wrapper);
            LogUtils.d("create new x5 webview instance.");
        } else {
            MutableContextWrapper wrapper = (MutableContextWrapper) webView.getContext();
            wrapper.setBaseContext(context);
            LogUtils.d("obtain x5 webview instance from pool.");
        }
        return webView;
    }

    public static FastWebView acquire(Context context) {
        FastWebView webView = sPool.acquire();
        if (webView == null) {
            MutableContextWrapper wrapper = new MutableContextWrapper(context);
            webView = new FastWebView(wrapper);
            LogUtils.d("create new webview instance.");
        } else {
            MutableContextWrapper wrapper = (MutableContextWrapper) webView.getContext();
            wrapper.setBaseContext(context);
            LogUtils.d("obtain webview instance from pool.");
        }
        return webView;
    }

    public static void release(FastWebView webView) {
        if (webView == null) {
            return;
        }
        webView.release();
        MutableContextWrapper wrapper = (MutableContextWrapper) webView.getContext();
        wrapper.setBaseContext(wrapper.getApplicationContext());
        sPool.release(webView);
        LogUtils.d("release webview instance to pool.");
    }

    public static void releaseX5(FastX5WebView webView) {
        if (webView == null) {
            return;
        }
        webView.release();
        MutableContextWrapper wrapper = (MutableContextWrapper) webView.getContext();
        wrapper.setBaseContext(wrapper.getApplicationContext());
        sPoolX5.release(webView);
        LogUtils.d("release x5 webview instance to pool.");
    }
}
