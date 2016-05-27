package com.yiketang.android.base;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.yiketang.android.R;


/**
 * Created by WuXiang on 2016/3/31.
 * <p/>
 * ..
 */
public abstract class ToolbarActivity extends BaseActivity {
    protected ActionBar mActionBar;
    protected TextView mTitle;

    @Override
    protected void initBaseView() {
        initToolbar();
        initView();
    }

    public void initToolbar() {
        Toolbar toolbar = findView(R.id.activity_toolbar);
        mTitle = findView(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        if (null != mActionBar) {
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected abstract void initView();

    @Override
    public void setTitle(CharSequence title) {
        mTitle.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        this.setTitle(getText(titleId));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
