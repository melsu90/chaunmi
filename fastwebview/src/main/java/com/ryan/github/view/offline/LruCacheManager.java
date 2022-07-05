package com.ryan.github.view.offline;

import androidx.collection.LruCache;

import com.ryan.github.view.WebResource;

public class LruCacheManager {

    private LruCache<String, WebResource> mLruCache;
    private static volatile LruCacheManager sInstance;

    private volatile boolean hasInit = false;

    public static LruCacheManager getInstance() {
        if (sInstance == null) {
            synchronized (LruCacheManager.class) {
                if (sInstance == null) {
                    sInstance = new LruCacheManager();
                }
            }
        }
        return sInstance;
    }

    public synchronized void init(int memorySize) {
        if(hasInit) {
            return;
        }
        if (memorySize > 0) {
            mLruCache = new ResourceMemCache(memorySize);
            hasInit = true;
        }
    }


    public WebResource get(String key) {
        if(mLruCache == null) {
            return null;
        }
        return mLruCache.get(key);
    }


    public void put(String key, WebResource value) {
        if(mLruCache == null) {
            return;
        }
        mLruCache.put(key, value);
    }

    public void clear() {
        if(mLruCache != null) {
            mLruCache.evictAll();
        }
    }

    private static class ResourceMemCache extends LruCache<String, WebResource> {

        /**
         * @param maxSize for caches that do not override {@link #sizeOf}, this is
         *                the maximum number of entries in the cache. For all other caches,
         *                this is the maximum sum of the sizes of the entries in this cache.
         */
        ResourceMemCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, WebResource value) {
            int size = 0;
            if (value != null && value.getOriginBytes() != null) {
                size = value.getOriginBytes().length;
            }
            return size;
        }
    }
}
