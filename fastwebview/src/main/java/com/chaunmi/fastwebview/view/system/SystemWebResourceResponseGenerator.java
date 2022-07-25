package com.chaunmi.fastwebview.view.system;

import android.webkit.WebResourceResponse;

import com.chaunmi.fastwebview.view.base.BaseWebResourceResponseGenerator;

import java.io.InputStream;
import java.util.Map;

public class SystemWebResourceResponseGenerator extends BaseWebResourceResponseGenerator {
    @Override
    protected Object createWebResourceResponse(String mimeType, String encoding, InputStream data) {
        return new WebResourceResponse(mimeType, encoding, data);
    }

    @Override
    protected Object createWebResourceResponse(String mimeType, String encoding, int statusCode, String reasonPhrase, Map<String, String> responseHeaders, InputStream data) {
        return new WebResourceResponse(mimeType, encoding, statusCode, reasonPhrase, responseHeaders, data);
    }
}
