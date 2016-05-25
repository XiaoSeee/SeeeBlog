package com.yiketang.android.ui.adapter;

import android.content.Context;

import com.yiketang.android.R;
import com.yiketang.android.base.BaseReHolder;
import com.yiketang.android.base.SingleReAdapter;

import java.util.List;

/**
 * Created by Xiao.Se on 2016/5/25.
 * ..
 */
public class CourseListAdapter extends SingleReAdapter<String> {


    public CourseListAdapter(List<String> data, Context context) {
        super(data, context);
    }

    @Override
    public void bindItemData(BaseReHolder viewHolder, String data, int position) {
        viewHolder.setText(R.id.text_view, data);
    }


    @Override
    protected int getContentView() {
        return R.layout.course_item_layout;
    }

}
