package com.yiketang.android.presenter;

import com.yiketang.android.presenter.contract.PasswordContract;

/**
 * Created by Xiao.Se on 2016/5/22.
 * ..
 */
public class PasswordPresenter implements PasswordContract.Presenter {

    private PasswordContract.View mPasswordView;

    public PasswordPresenter(PasswordContract.View view) {
        mPasswordView = view;
        mPasswordView.setPresenter(this);
    }

    @Override
    public void getCode(String phone) {
        if (null == phone || phone.isEmpty() || phone.length() != 11) {
            mPasswordView.phoneNumError();
        }
    }

    @Override
    public void resetPassword(String phone, String code, String newPassword, String againPassword) {

        if (null == phone || phone.isEmpty() ||
                phone.length() != 11) {
            mPasswordView.phoneNumError();
        } else if (null == code || code.isEmpty()) {
            mPasswordView.codeNumError();
        } else if (null == newPassword || newPassword.isEmpty() || newPassword.length() < 6) {
            mPasswordView.passwordShortError();
        } else if (null == againPassword || !newPassword.equals(againPassword)) {
            mPasswordView.passwordDiffError();
        }
    }

    @Override
    public void createUser(String phone, String code, String newPassword, String againPassword) {

    }

    @Override
    public void start() {

    }
}
