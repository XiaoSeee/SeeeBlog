package com.yiketang.android.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.yiketang.android.R;


/**
 * Created by WuXiang on 2016/3/31.
 * <p/>
 * ..
 */
public abstract class ToolbarActivity extends BaseActivity {
    public ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initBaseView();
        initView();
        createPresenter();
    }

    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void createPresenter();

    public void initBaseView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
