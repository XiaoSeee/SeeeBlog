package com.yiketang.android.ui.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.EditText;

import com.yiketang.android.R;
import com.yiketang.android.base.ToolbarActivity;
import com.yiketang.android.presenter.contract.LoginContract;
import com.yiketang.android.presenter.LoginPresenter;
import com.yiketang.android.util.DialogUtil;
import com.yiketang.android.util.LogUtil;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends ToolbarActivity implements LoginContract.View {

    private LoginContract.Presenter mPresenter;
    // UI references.
    private TextInputLayout mPhoneLayout;
    private TextInputLayout mPasswordLayout;
    private EditText mInputPhone;
    private EditText mInputPassword;

    private SwipeRefreshLayout srl;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        // Set up the login form.
        mPhoneLayout = findView(R.id.email_layout);
        mPasswordLayout = findView(R.id.password_layout);
        mInputPhone = findView(R.id.input_phone);
        mInputPassword = findView(R.id.input_password);

        srl = findView(R.id.login_srf);
        srl.setEnabled(false);

        findView(R.id.sign_in_button).setOnClickListener(this);
        findView(R.id.forget_password).setOnClickListener(this);
        findView(R.id.user_register).setOnClickListener(this);
    }

    @Override
    protected void createPresenter() {
        new LoginPresenter(this);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sign_in_button:
                mPresenter.loginPhone(
                        mInputPhone.getText().toString(),
                        mInputPassword.getText().toString());
                break;
            case R.id.forget_password:
                Intent intent_forget = new Intent(mContext, ForgetPasswordActivity.class);
                startActivity(intent_forget);
                break;
            case R.id.user_register:
                Intent intent_reg = new Intent(mContext, UserRegisterActivity.class);
                startActivity(intent_reg);
                break;
        }
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void loginSuccess() {
        Intent intent_reg = new Intent(mContext, UserRegisterActivity.class);
        startActivity(intent_reg);
    }

    @Override
    public void emptyPhoneError() {
        mPhoneLayout.setError(getString(R.string.phone_is_null));
        mPasswordLayout.setError(null);
    }

    @Override
    public void emptyPasswordError() {
        mPasswordLayout.setError(getString(R.string.password_is_null));
        mPhoneLayout.setError(null);
    }

    @Override
    public void loginFail() {
        mPasswordLayout.setError(getString(R.string.login_fail_1));
        mPhoneLayout.setError(null);
    }

    @Override
    public void showBanner(String imgUrl) {

    }


}

