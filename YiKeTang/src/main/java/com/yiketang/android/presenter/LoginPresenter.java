package com.yiketang.android.presenter;

import com.yiketang.android.presenter.contract.LoginContract;

/**
 * Created by Xiao.Se on 2016/5/21.
 * ..
 */
public class LoginPresenter implements LoginContract.Presenter {


    private final LoginContract.View mLoginView;

    public LoginPresenter(LoginContract.View view) {
        mLoginView = view;
        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {
//        mLoginView.setLoadingIndicator(true);
    }

    @Override
    public void loginPhone(String phone, String password) {

        if (null == phone || phone.isEmpty()) {
            mLoginView.emptyPhoneError();
        } else if (null == password || password.isEmpty()) {
            mLoginView.emptyPasswordError();
        } else {
            mLoginView.loginSuccess();
        }
    }
}
