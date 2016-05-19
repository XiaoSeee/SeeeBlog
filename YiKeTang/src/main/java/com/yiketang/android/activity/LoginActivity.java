package com.yiketang.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.yiketang.android.R;
import com.yiketang.android.base.BaseActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    // UI references.
    private TextInputLayout mEmailLayout;
    private TextInputLayout mPasswordLayout;
    private EditText mInputEmail;
    private EditText mInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

    }

    private void initView() {
        // Set up the login form.
        mEmailLayout = findView(R.id.email_layout);
        mPasswordLayout = findView(R.id.password_layout);
        mInputEmail = findView(R.id.input_phone);
        mInputPassword = findView(R.id.input_password);

        findView(R.id.sign_in_button).setOnClickListener(this);
        findView(R.id.forget_password).setOnClickListener(this);
        findView(R.id.user_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sign_in_button:
                submit();
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

    private void submit() {
        boolean isEmptyEmail = isEmptyEmail();
        boolean isEmptyPassword = isEmptyPassword();
        if (isEmptyEmail) {
            mEmailLayout.setError(getString(R.string.phone_is_null));
            mPasswordLayout.setError(null);
        } else if (isEmptyPassword) {
            mPasswordLayout.setError(getString(R.string.password_is_null));
            mEmailLayout.setError(null);
        } else {

        }
    }

    private boolean isEmptyEmail() {
        return mInputEmail.getText() == null
                || mInputEmail.getText().toString().isEmpty();

    }

    private boolean isEmptyPassword() {
        return mInputPassword.getText() == null
                || mInputPassword.getText().toString().isEmpty();

    }

}

