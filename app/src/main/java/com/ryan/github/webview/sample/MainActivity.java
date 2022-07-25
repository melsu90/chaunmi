package com.ryan.github.webview.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ryan.github.view.FastWebView;
import com.ryan.github.view.FastWebViewPool;
import com.ryan.github.view.config.CacheConfig;
import com.ryan.github.view.config.FastCacheMode;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;

public class MainActivity extends Activity {

    public static boolean sUseWebViewPool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initX5();
        CheckBox checkBox = findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sUseWebViewPool = isChecked;
                if(isChecked) {
                    FastWebViewPool.prepare(MainActivity.this);
                    FastWebViewPool.prepareX5(MainActivity.this);
                }
            }
        });


        findViewById(R.id.test_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WebViewActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.test_btn_x5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), X5WebViewActivity.class);
                startActivity(intent);
            }
        });

        CacheConfig config = new CacheConfig.Builder(this)
                .setCacheDir(getCacheDir() + File.separator + "custom")
                //        .setExtensionFilter(new CustomMimeTypeFilter())
                //        .setAssetsDir("static")
                .build();
    //    FastWebView.preload(this, WebViewActivity.url, FastCacheMode.FORCE, config);
    }

    private void initX5() {
        QbSdk.initX5Environment(this,  new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                // 内核初始化完成，可能为系统内核，也可能为系统内核
                Log.i("FastWebView", "onCoreInitFinished");
            }

            /**
             * 预初始化结束
             * 由于X5内核体积较大，需要依赖网络动态下发，所以当内核不存在的时候，默认会回调false，此时将会使用系统内核代替
             * @param isX5 是否使用X5内核
             */
            @Override
            public void onViewInitFinished(boolean isX5) {
                Log.i("FastWebView", "onViewInitFinished isX5: " + isX5);
            }
        });

    }
}


