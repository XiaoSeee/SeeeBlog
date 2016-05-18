package com.yiketang.android.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.yiketang.android.model.ThirdPartyInfo;
import com.yiketang.android.util.Constants;


/**
 * Created by WuXiang on 2016/3/23.
 */
public class Preferences {
    private SharedPreferences spf;
    private SharedPreferences.Editor editor;
    private static Preferences instance;

    public final String LOGIN_STATE = "login_state";//是否登录
    public final String LOGIN_NAME = "login_name";//登录账号
    public final String LOGIN_PASSWORD = "login_password";//登录密码
    public final String USER_NAME = "user_name";//用户的昵称
    public final String USER_IMG = "user_img";//用户的头像
    public final String ThirdParty_uid = "thirdpartyUid";//第三方uid，唯一标识
    public final String ThirdParty_type = "thirdpartyType";//第三方类型，如QQ,WB等
    public final String ThirdParty_token = "thirdpartyToken";//第三方token
    public final String ThirdParty_name = "thirdpartyusername";//第三方name
    public final String ThirdParty_gender = "thirdpartygender";//第三方性别

    public static synchronized Preferences getInstance(Context context) {
        if (instance == null) {
            instance = new Preferences(context);
        }
        return instance;
    }

    private Preferences(Context context) {
        spf = context.getSharedPreferences(Constants.SharedName, Context.MODE_PRIVATE);
        editor = spf.edit();
    }

    public void setLoginState(int state) {
        editor.putInt(LOGIN_STATE, state);
        editor.commit();
    }

    public int getLoginState() {
        return spf.getInt(LOGIN_STATE, Constants.LOGIN_STATE_OUT);
    }

    public void setLoginName(String name) {
        editor.putString(LOGIN_NAME, name);
        editor.commit();
    }

    public String getLoginName() {
        return spf.getString(LOGIN_NAME, "null");
    }

    public void setLoginPassword(String password) {
        editor.putString(LOGIN_PASSWORD, password);
        editor.commit();
    }

    public String getLoginPassword() {
        return spf.getString(LOGIN_PASSWORD, "null");
    }

    public void setUserName(String val) {
        editor.putString(USER_NAME, val);
        editor.commit();
    }

    public String getUserName() {
        return spf.getString(USER_NAME, "null");
    }

    public void setUserImg(String val) {
        editor.putString(USER_IMG, val);
        editor.commit();
    }

    public String getUserImg() {
        return spf.getString(USER_IMG, "null");
    }

    //保存第三方登陆的信息
    public void setThirdPartyInfo(ThirdPartyInfo info) {
        if (info == null) {
            editor.putString(ThirdParty_uid, "");
            editor.putString(ThirdParty_type, "");
            editor.putString(ThirdParty_token, "");
            editor.putString(ThirdParty_name, "");
            editor.putString(ThirdParty_gender, "");
        } else {
            editor.putString(ThirdParty_uid, info.getUid());
            editor.putString(ThirdParty_type, info.getType());
            editor.putString(ThirdParty_token, info.getToken());
            editor.putString(ThirdParty_name, info.getUsername());
            editor.putString(ThirdParty_gender, info.getGender());
        }
        editor.commit();
    }

    public ThirdPartyInfo getThirdPartyInfo() {
        ThirdPartyInfo info = new ThirdPartyInfo();
        String uid = spf.getString(ThirdParty_uid, "").trim();
        if (uid.length() > 0) {
            info.setUid(uid);
            info.setType(spf.getString(ThirdParty_type, "").trim());
            info.setToken(spf.getString(ThirdParty_token, "").trim());
            info.setUsername(spf.getString(ThirdParty_name, "").trim());
            info.setGender(spf.getString(ThirdParty_gender, "").trim());
            return info;
        } else {
            return null;
        }
    }
}
