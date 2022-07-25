package com.ryan.github.view.offline;

import android.webkit.WebResourceResponse;

import com.ryan.github.view.base.BaseWebResourceResponseGenerator;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by Ryan
 * at 2019/10/8
 */
public class DefaultWebResponseGenerator extends BaseWebResourceResponseGenerator {

    @Override
    protected Object createWebResourceResponse(String mimeType, String encoding, InputStream data) {
        return new WebResourceResponse(mimeType, encoding, data);
    }

    @Override
    protected Object createWebResourceResponse(String mimeType, String encoding, int statusCode, String reasonPhrase, Map<String, String> responseHeaders, InputStream data) {
        return new WebResourceResponse(mimeType, encoding, statusCode, reasonPhrase, responseHeaders, data);
    }
}
