package com.yiketang.android.base;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.yiketang.android.R;

/**
 * Created by Xiao.Se on 2016/5/27.
 * ..
 */
public abstract class ToolbarFragment extends BaseFragment {
    protected TextView mTitle;
    protected Toolbar mToolbar;

    @Override
    protected void initView() {
        initToolbar();
        initToolbarView();
    }

    protected abstract void initToolbarView();

    public void initToolbar() {
        mToolbar = findView(R.id.activity_toolbar);
        mTitle = findView(R.id.toolbar_title);
    }

    public void setTitle(CharSequence title) {
        mTitle.setText(title);
    }

    public void setTitle(int titleId) {
        this.setTitle(getText(titleId));
    }
}
