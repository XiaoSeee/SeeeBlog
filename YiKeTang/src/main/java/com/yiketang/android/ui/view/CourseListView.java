package com.yiketang.android.ui.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.yiketang.android.R;
import com.yiketang.android.base.BaseReAdapter;
import com.yiketang.android.ui.adapter.CourseListAdapter;
import com.yiketang.android.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiao.Se on 2016/5/25.
 * ..
 */
public class CourseListView extends LinearLayout {
    public CourseListView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        List<String> date = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            date.add("理科数学");
        }
        View.inflate(context, R.layout.course_list_view, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.course_rcView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        CourseListAdapter adapter = new CourseListAdapter(date, context);
        adapter.setOnItemClickListener(new BaseReAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LogUtil.d("position=" + position);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
