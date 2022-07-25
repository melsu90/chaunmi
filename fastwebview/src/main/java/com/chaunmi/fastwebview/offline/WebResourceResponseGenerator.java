package com.chaunmi.fastwebview.offline;


import com.chaunmi.fastwebview.view.base.WebResource;

/**
 * Created by Ryan
 * at 2019/10/8
 */
public interface WebResourceResponseGenerator {

     Object generate(WebResource resource, String urlMime);
}
