package com.yiketang.android.util;

import com.yiketang.android.BuildConfig;

public class Constants {
    public static final Boolean showLog = BuildConfig.LOG_DEBUG;
    public static final String HOST = BuildConfig.HOST;

    public static final String cacheDir = "YiKeTang";
    public static final String SharedName = "yiketang_share";


    public static final int LOGIN_STATE_OUT = 0;//未登录
    public static final int LOGIN_STATE_IN = 1;//手机号登陆
    public static final int LOGIN_STATE_THIRD = 2;//第三方登陆


}
