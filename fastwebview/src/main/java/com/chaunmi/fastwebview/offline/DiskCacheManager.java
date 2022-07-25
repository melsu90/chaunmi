package com.chaunmi.fastwebview.offline;


import com.chaunmi.fastwebview.utils.lru.DiskLruCache;

import java.io.File;
import java.io.IOException;

public class DiskCacheManager {
    public static final int ENTRY_META = 0; //存状态码及headers相关
    public static final int ENTRY_BODY = 1; //具体数值
    public static final int ENTRY_COUNT = 2;


    private DiskLruCache mDiskLruCache;
    private static volatile DiskCacheManager sInstance;

    private volatile boolean hasInit = false;

    public static DiskCacheManager getInstance() {
        if (sInstance == null) {
            synchronized (DiskCacheManager.class) {
                if (sInstance == null) {
                    sInstance = new DiskCacheManager();
                }
            }
        }
        return sInstance;
    }

    public synchronized void init(File fileDir, int version, long maxSize) {
        if(hasInit) {
            return;
        }
        if (mDiskLruCache != null && !mDiskLruCache.isClosed()) {
            return;
        }
        try {
            //ENTRY_COUNT是指一个key对应几个value
            mDiskLruCache = DiskLruCache.open(fileDir, version, ENTRY_COUNT, maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkAvailable() {
        return mDiskLruCache != null && !mDiskLruCache.isClosed();
    }


    public  DiskLruCache.Editor edit(String key) throws IOException{
        if(!checkAvailable()) {
            return null;
        }
        return mDiskLruCache.edit(key);
    }

    public DiskLruCache.Snapshot get(String key) throws IOException {
        if(!checkAvailable()) {
            return null;
        }
        return mDiskLruCache.get(key);
    }

    public void close() throws IOException{
        if(!checkAvailable()) {
            return;
        }
        hasInit = false;
        mDiskLruCache.close();
        mDiskLruCache = null;
    }

    public void remove(String key) throws IOException{
        if(!checkAvailable()) {
            return;
        }
        mDiskLruCache.remove(key);
    }

    /**
     * 删除缓存目录
     * @param directory
     * @param appVersion
     * @param maxSize
     * @throws IOException
     */
    public void clear(File directory, int appVersion, long maxSize) throws IOException {
        init(directory, appVersion, maxSize);
        if(!checkAvailable()) {
            return;
        }
        mDiskLruCache.delete();
    }
}
