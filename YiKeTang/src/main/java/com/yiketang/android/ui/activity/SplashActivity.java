package com.yiketang.android.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yiketang.android.R;
import com.yiketang.android.base.BaseActivity;
import com.yiketang.android.util.Constants;


/**
 * Created by WuXiang on 2016/3/22.
 * 闪屏页面
 */
public class SplashActivity extends BaseActivity {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
//                case HttpClientUtil.LOGIN_SUCCESS:
//                case HttpClientUtil.THIRD_LOGIN_SUCCESS:
//                    start();
//                    break;
//                case HttpClientUtil.LOGIN_ERROR:
//                case HttpClientUtil.THIRD_LOGIN_ERROR:
//                    mSpf.setLoginState(Constants.LOGIN_STATE_OUT);
//                    Utils.showToastShort(mContext, getString(R.string.login_error));
//                    start();
//                    break;
            }
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.splash_layou;
    }

    @Override
    protected void initBaseView() {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        window.setAttributes(params);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loginIn();
            }
        }, 3000);
    }

    @Override
    protected void createPresenter() {

    }


    private void loginIn() {
        if (mSpf.getLoginState() == Constants.LOGIN_STATE_IN) {
//            HttpClientUtil.login(mContext, handler, mSpf.getLoginName(), mSpf.getLoginPassword(), false);
        } else if (mSpf.getLoginState() == Constants.LOGIN_STATE_THIRD) {
//            HttpClientUtil.loginThirdParty(mContext, handler, mSpf.getThirdPartyInfo(), false);
        } else {
            loginError();
        }

    }

    private void loginSuccess() {
//        Intent intent = new Intent(mContext, MainActivity.class);
//        startActivity(intent);
//        finish();
    }

    private void loginError() {
        Intent intent = new Intent(mContext, MainActivity.class);
//        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
