package com.chaunmi.fastwebview.utils;


/**
 * Created by Ryan
 * at 2019/10/8
 */
public class LogUtils {

    private static final String TAG = "FastWebView";
    public static boolean DEBUG = true;

    private static ILogger logger = new DefaultLoggerImp();

    public static void setLogger(ILogger loggerImp) {
        logger = loggerImp;
    }


    public static void d(String message) {
        if(logger != null) {
            logger.d(TAG, message, null);
        }
    }

    public static void d(String msg, Throwable tr) {
        if(logger != null) {
            logger.d(TAG, msg, tr);
        }
    }

    public static void e(String message) {
        if(logger != null) {
            logger.e(TAG, message, null);
        }
    }

    public static void e(String msg, Throwable tr) {
        if(logger != null) {
            logger.e(TAG, msg, tr);
        }
    }

    public static void i(String msg) {
        if(logger != null) {
            logger.i(TAG, msg, null);
        }
    }

    public static void i(String msg, Throwable tr) {
        if(logger != null) {
            logger.i(TAG, msg, tr);
        }
    }

    public static void w(String msg) {
        if(logger != null) {
            logger.w(TAG, msg, null);
        }
    }

    public static void w(String msg, Throwable tr) {
        if(logger != null) {
            logger.w(TAG, msg, tr);
        }
    }

}
