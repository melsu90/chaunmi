package com.ryan.github.view.offline;

import android.webkit.WebResourceResponse;

import com.ryan.github.view.WebResource;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by Ryan
 * at 2019/10/8
 */
public interface WebResourceResponseGenerator {

     Object generate(WebResource resource, String urlMime);
}
