package com.yiketang.android.ui.fragment;

import android.widget.LinearLayout;

import com.yiketang.android.R;
import com.yiketang.android.base.BaseFragment;
import com.yiketang.android.ui.view.CourseListView;

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
        LinearLayout ll = findView(R.id.course_sc);
        CourseListView view = new CourseListView(mContext);
        ll.addView(view);
        ll.addView(new CourseListView(mContext));
        ll.addView(new CourseListView(mContext));
        ll.addView(new CourseListView(mContext));

    }

    @Override
    protected void createPresenter() {

    }
}
