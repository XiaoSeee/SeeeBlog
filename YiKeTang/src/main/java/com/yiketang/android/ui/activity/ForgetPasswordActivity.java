package com.yiketang.android.ui.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yiketang.android.R;
import com.yiketang.android.base.ToolbarActivity;
import com.yiketang.android.presenter.PasswordPresenter;
import com.yiketang.android.presenter.contract.PasswordContract;
import com.yiketang.android.ui.view.MyCountDownTimer;
import com.yiketang.android.util.LogUtil;

/**
 * Created by WuXiang on 2016/5/19.
 * ..
 */
public class ForgetPasswordActivity extends ToolbarActivity implements PasswordContract.View {
    private PasswordContract.Presenter mPresenter;
    private Button fgt_code;
    private MyCountDownTimer cTimer;

    private TextInputLayout phone_layout, code_layout, password_layout, again_layout;
    private EditText phone_edit, code_edit, password_edit, again_edit;
    private SwipeRefreshLayout forget_srf;

    @Override
    protected int getContentView() {
        return R.layout.user_forget_password;
    }

    @Override
    protected void initView() {

        phone_layout = findView(R.id.phone_layout);
        phone_edit = findView(R.id.phone_edit);

        code_layout = findView(R.id.code_layout);
        code_edit = findView(R.id.code_edit);

        password_layout = findView(R.id.password_layout);
        password_edit = findView(R.id.password_edit);

        again_layout = findView(R.id.again_layout);
        again_edit = findView(R.id.again_edit);

        fgt_code = findView(R.id.fgt_code);
        fgt_code.setOnClickListener(this);
        cTimer = new MyCountDownTimer(60 * 1000, 1000, fgt_code, getString(R.string.register_code));
        findView(R.id.fgt_login).setOnClickListener(this);

        forget_srf = findView(R.id.forget_srf);
        forget_srf.setEnabled(false);
    }

    @Override
    protected void createPresenter() {
        new PasswordPresenter(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fgt_code:
                mPresenter.getCode(phone_edit.getText().toString());
                break;
            case R.id.fgt_login:
                mPresenter.resetPassword(
                        phone_edit.getText().toString(),
                        code_edit.getText().toString(),
                        password_edit.getText().toString(),
                        again_edit.getText().toString());
                break;
        }
    }

    private void resetError() {
        phone_layout.setError(null);
        code_layout.setError(null);
        password_layout.setError(null);
        again_layout.setError(null);
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        forget_srf.post(new Runnable() {
            @Override
            public void run() {
                forget_srf.setRefreshing(active);
            }
        });
    }

    @Override
    public void setTimeIndicator(boolean active) {
        if (active) {
            cTimer.start();
        } else {
            cTimer.stop();
        }
    }

    @Override
    public void phoneNumError() {
        resetError();
        phone_layout.setError(getString(R.string.phone_is_error));
    }

    @Override
    public void codeNumError() {
        resetError();
        code_layout.setError(getString(R.string.register_code_error));
    }

    @Override
    public void passwordShortError() {
        resetError();
        password_layout.setError(getString(R.string.register_password));
    }


    @Override
    public void passwordDiffError() {
        resetError();
        again_layout.setError(getString(R.string.password_diff_error));
    }

    @Override
    public void resetFail() {

    }

    @Override
    public void createFail() {

    }

    @Override
    public void setPresenter(PasswordContract.Presenter presenter) {
        mPresenter = presenter;
    }

}
