package com.yiketang.android.activity;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yiketang.android.R;
import com.yiketang.android.base.ToolbarActivity;
import com.yiketang.android.view.MyCountDownTimer;

/**
 * Created by WuXiang on 2016/5/19.
 * ..
 */
public class ForgetPasswordActivity extends ToolbarActivity {
    private Button fgt_code;
    private MyCountDownTimer cTimer;

    private TextInputLayout phone_layout, code_layout, password_layout, again_layout;
    private EditText phone_edit, code_edit, password_edit, again_edit;

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
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fgt_code:
                getCode();
                break;
            case R.id.fgt_login:
                cTimer.stop();
                break;
        }
    }

    private void getCode() {

    }
}
