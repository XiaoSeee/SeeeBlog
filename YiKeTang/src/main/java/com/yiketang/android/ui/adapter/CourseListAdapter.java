package com.yiketang.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiketang.android.R;
import com.yiketang.android.base.BaseReAdapter;

import java.util.List;

/**
 * Created by WuXiang on 2016/5/25.
 * ..
 */
public class CourseListAdapter extends BaseReAdapter<String, CourseListAdapter.CourseHolder> {


    public CourseListAdapter(List<String> data, Context context) {
        super(data, context);
    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CourseHolder(LayoutInflater.from(mContext).inflate(R.layout.course_item_layout, parent, false));
    }

    @Override
    protected void bindItemData(CourseHolder viewHolder, String data, int position) {
        viewHolder.textView.setText(data);
    }

    static class CourseHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public CourseHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_view);
        }
    }
}
