package com.ryan.github.view.config;

/**
 * Created by Ryan
 * at 2019/11/1
 */
public enum FastCacheMode {
    DEFAULT,  //和原生一样
    NORMAL,   //采用okhttp缓存，缓存模式与WebView设置的缓存模式一致，缓存空间最大100M
    FORCE     //可过滤缓存，指定缓存文件类型
}
