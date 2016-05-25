package com.yiketang.android.ui.fragment;

import com.yiketang.android.R;
import com.yiketang.android.base.BaseFragment;

/**
 * Created by WuXiang on 2016/5/25.
 * ..
 */
public class CoursePageFragment extends BaseFragment {

    public static CoursePageFragment newInstance() {
        return new CoursePageFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.course_page_layou;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void createPresenter() {

    }
}
