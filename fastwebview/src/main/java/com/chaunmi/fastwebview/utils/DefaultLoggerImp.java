package com.chaunmi.fastwebview.utils;

import android.util.Log;

public class DefaultLoggerImp implements ILogger{
    @Override
    public void e(String tag, String msg, Throwable tr) {
        if(LogUtils.DEBUG) {
            Log.e(tag, msg, tr);
        }
    }

    @Override
    public void d(String tag, String msg, Throwable tr) {
        if(LogUtils.DEBUG) {
            Log.d(tag, msg, tr);
        }
    }

    @Override
    public void i(String tag, String msg, Throwable tr) {
        if(LogUtils.DEBUG) {
            Log.i(tag, msg, tr);
        }
    }

    @Override
    public void w(String tag, String msg, Throwable tr) {
        if(LogUtils.DEBUG) {
            Log.w(tag, msg, tr);
        }
    }
}
