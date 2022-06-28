package com.ryan.github.webview.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ryan.github.view.FastWebView;
import com.ryan.github.view.config.CacheConfig;
import com.ryan.github.view.config.FastCacheMode;

import java.io.File;

public class MainActivity extends Activity {

    public static boolean sUseWebViewPool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckBox checkBox = findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sUseWebViewPool = isChecked;
            }
        });

        findViewById(R.id.test_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WebViewActivity.class);
                startActivity(intent);
            }
        });

        CacheConfig config = new CacheConfig.Builder(this)
                .setCacheDir(getCacheDir() + File.separator + "custom")
                //        .setExtensionFilter(new CustomMimeTypeFilter())
                //        .setAssetsDir("static")
                .build();
        FastWebView.preload(this, WebViewActivity.url, FastCacheMode.FORCE, config);
    }
}


