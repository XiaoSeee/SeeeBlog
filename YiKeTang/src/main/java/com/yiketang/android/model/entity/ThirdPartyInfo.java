package com.yiketang.android.model.entity;

/**
 * Created by WuXiang on 2016/1/31.
 * 第三方登陆的信息
 */
public class ThirdPartyInfo {
    public String uid, type, token, username, gender;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "uid=" + uid + ";type=" + type + ";token=" + token + ";username=" + username + ";gender=" + gender;
    }
}
