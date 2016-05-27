package com.yiketang.android.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.yiketang.android.R;
import com.yiketang.android.base.BaseFragment;
import com.yiketang.android.base.BaseReAdapter;
import com.yiketang.android.base.ToolbarFragment;
import com.yiketang.android.ui.adapter.MajorListAdapter;
import com.yiketang.android.ui.view.CourseListView;
import com.yiketang.android.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WuXiang on 2016/5/25.
 * ..
 */
public class CoursePageFragment extends ToolbarFragment {

    public static CoursePageFragment newInstance() {
        return new CoursePageFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.course_page_layou;
    }

    @Override
    protected void initToolbarView() {

        setTitle(R.string.forget_finish);

        mToolbar.inflateMenu(R.menu.home_page_menu);

        LinearLayout ll = findView(R.id.course_sc);
        CourseListView view = new CourseListView(mContext);
        ll.addView(view);
        ll.addView(new CourseListView(mContext));


        List<String> date = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            date.add("理科数学");
        }
        RecyclerView major_rc = findView(R.id.major_rc);
        major_rc.setLayoutManager(new LinearLayoutManager(mContext));
        MajorListAdapter adapter = new MajorListAdapter(date, mContext);
        adapter.setOnItemClickListener(new BaseReAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LogUtil.d("position=" + position);
            }
        });
        major_rc.setAdapter(adapter);
        //support library 23.2.1 的一个Bug,调用notifyItemChanged()会导致RecyclerView自动回滚到顶部
        //需要设置下面这个属性,暂时不知道原因
        //见：https://code.google.com/p/android/issues/detail?id=203574
        major_rc.setHasFixedSize(true);

    }

    @Override
    protected void createPresenter() {

    }
}
