package com.yiketang.android.ui.fragment;

import com.yiketang.android.R;
import com.yiketang.android.base.ToolbarFragment;

/**
 * Created by Xiao.Se on 2016/5/28.
 * ..
 */
public class MyPageFragment extends ToolbarFragment {

    public static MyPageFragment newInstance() {
        return new MyPageFragment();
    }

    @Override
    protected void initToolbarView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.my_page_layou;
    }

    @Override
    protected void createPresenter() {

    }
}
