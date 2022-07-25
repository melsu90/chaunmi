package com.chaunmi.fastwebview.loader;


import com.chaunmi.fastwebview.view.base.WebResource;

/**
 * Created by Ryan
 * 2018/2/7 下午7:53
 */
public interface ResourceLoader {

    WebResource getResource(SourceRequest request);

}



