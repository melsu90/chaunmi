package com.chaunmi.fastwebview.offline;


import com.chaunmi.fastwebview.view.base.WebResource;

/**
 * Created by Ryan
 * at 2019/9/27
 */
public interface ResourceInterceptor {

    WebResource load(Chain chain);

}
