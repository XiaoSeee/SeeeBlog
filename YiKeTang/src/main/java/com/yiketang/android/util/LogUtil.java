package com.yiketang.android.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * 信息调试类，用于记录一些调试信息和错误日志信息
 *
 * @author zhiwu_yan
 * @version 1.0
 * @since 2015年05月26日
 */
public class LogUtil {

    private final static String TAG = "LogUtil";
    public static boolean LOGGABLE = Constants.showLog;


    /**
     * 打印DEBUG信息
     *
     * @param tag
     * @param str
     */
    public static void d(String tag, String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            Log.d(tag, str);
        }
    }

    public static void d(String tag, byte[] data) {
        if (LOGGABLE && data != null && data.length > 0) {
            final StringBuilder stringBuilder = new StringBuilder(data.length);
            for (byte byteChar : data) {
                stringBuilder.append(String.format("%02X ", byteChar));
            }
            Log.d(tag, stringBuilder.toString());
        }
    }

    /**
     * 打印debug级别的log
     *
     * @param str 内容
     */
    public static void d(String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            Log.d(TAG, str);
        }
    }

    /**
     * 打印warning的log
     *
     * @param tag tag标签
     * @param str 内容
     */
    public static void w(String tag, String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            Log.w(tag, str);
        }
    }


    /**
     * 打印warning级别的log
     *
     * @param str 内容
     */
    public static void w(String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            Log.w(TAG, str);
        }
    }

    /**
     * 打印error级别的log
     *
     * @param tag tag标签
     * @param str 内容
     */
    public static void e(String tag, String str, Throwable tr) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            Log.e(tag, str);
            tr.printStackTrace();
        }
    }

    /**
     * 打印error级别的log
     *
     * @param tag tag标签
     * @param str 内容
     */
    public static void e(String tag, String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            Log.e(tag, str);
        }
    }

    /**
     * 打印error级别的log
     *
     * @param str 内容
     */
    public static void e(String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            Log.e(TAG, str);
        }
    }

    /**
     * 打印info级别的log
     *
     * @param tag tag标签
     * @param str 内容
     */
    public static void i(String tag, String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            Log.i(tag, str);
        }
    }

    /**
     * 打印info级别的log
     *
     * @param str 内容
     */
    public static void i(String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            Log.i(TAG, str);
        }
    }

    /**
     * 打印verbose级别的log
     *
     * @param tag tag标签
     * @param str 内容
     */
    public static void v(String tag, String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            Log.v(tag, str);
        }
    }

    /**
     * 打印verbose级别的log
     *
     * @param str 内容
     */
    public static void v(String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            Log.v(TAG, str);
        }
    }

}
