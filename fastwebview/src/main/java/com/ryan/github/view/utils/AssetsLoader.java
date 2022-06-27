package com.ryan.github.view.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArraySet;

public class AssetsLoader {

    private static volatile AssetsLoader assetsLoader;
    private CopyOnWriteArraySet<String> mAssetResSet;
    private String mAssetsDir = "";

    private static volatile boolean hasInit = false;

    public static AssetsLoader getInstance() {
        if (assetsLoader == null) {
            synchronized (AssetsLoader.class) {
                if (assetsLoader == null) {
                    assetsLoader = new AssetsLoader();
                }
            }
        }
        return assetsLoader;
    }

    private AssetsLoader() {
    }

    /**
     * 初始化assets相关数据
     */
    public synchronized void init(Context context, String assetsDir) {
        if (hasInit) {
            return;
        }
        this.mAssetsDir = assetsDir;
        this.mAssetResSet = new CopyOnWriteArraySet<String>();
        long now = System.currentTimeMillis();
        initResourceData(context, mAssetsDir);
        LogUtils.d(" initResourceData cost: " + (System.currentTimeMillis() - now));
        hasInit = true;
    }


    private void addAssetsFilePath(String filePath) {
        String flag = mAssetsDir + File.separator;
        String path = filePath;
        int pos = path.indexOf(flag);
        if (pos >= 0) { //去掉dir目录信息
            path = path.substring(pos + flag.length());
        }
        mAssetResSet.add(path);
    }

    /**
     * 遍历初始化assets路径信息
     *
     * @param dir
     */
    private void initResourceData(Context context, String dir) {
        if (TextUtils.isEmpty(dir) || context == null) {
            return;
        }
        try {
            LinkedList<String> list = new LinkedList<String>();
            String[] resData = context.getAssets().list(dir);
            for (String res : resData) {
                String sub = dir + File.separator + res;
                String[] subs = context.getAssets().list(sub);
                if (subs.length == 0) {
                    addAssetsFilePath(sub);
                } else {
                    list.add(sub);
                }
            }
            while (!list.isEmpty()) {
                String last = list.removeFirst();
                String[] subs = context.getAssets().list(last);
                if (subs.length == 0) {
                    addAssetsFilePath(last);
                } else {
                    for (String sub : subs) {
                        String[] subSubs = context.getAssets().list(last + File.separator + sub);
                        if (subSubs.length == 0) {
                            addAssetsFilePath(last + File.separator + sub);
                        } else {
                            list.add(last + File.separator + sub);
                        }
                    }
                }
            }
        } catch (IOException e) {
            LogUtils.e(e.toString());
        }
    }

    /**
     * 获取url的path信息
     *
     * @param url
     * @return
     */
    private String getUrlPath(String url) {
        String path = "";
        try {
            URL uri = new URL(url);
            path = uri.getPath();
            if (path.startsWith("/")) {
                if (path.length() == 1) {
                    return path;
                }
                path = path.substring(1);
            }
        } catch (MalformedURLException e) {
            LogUtils.e(e.toString());
        }
        return path;
    }

    /**
     * @param context
     * @param url
     * @return
     */
    public InputStream getResByUrl(Context context, String url) {
        String path = getUrlPath(url);
        if (TextUtils.isEmpty(path) || context == null || TextUtils.isEmpty(mAssetsDir)) {
            return null;
        }
        if (mAssetResSet != null) {
            for (String assetPath : mAssetResSet) {
                if (path.endsWith(assetPath)) {
                    return getAssetFileStream(context, mAssetsDir + File.separator + assetPath);
                }
            }
        }
        return null;
    }

    private InputStream getAssetFileStream(Context context, String path) {
        try {
            return context.getAssets().open(path);
        } catch (IOException e) {
            LogUtils.e(e.toString());
        }
        return null;
    }
}
