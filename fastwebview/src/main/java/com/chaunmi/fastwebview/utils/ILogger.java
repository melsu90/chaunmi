package com.chaunmi.fastwebview.utils;


import androidx.annotation.Nullable;

public interface ILogger {
    public void e(String tag, String msg, @Nullable Throwable tr);
    public void d(String tag, String msg, @Nullable Throwable tr);
    public void i(String tag, String msg, @Nullable Throwable tr);
    public void w(String tag, String msg, @Nullable Throwable tr);
}
