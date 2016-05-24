package com.yiketang.android.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.yiketang.android.R;

/**
 * Created by WuXiang on 2016/5/23.
 * ..
 */
public class HomeBannerView extends RelativeLayout implements View.OnClickListener {
    private TaskItemListener mListener;

    public HomeBannerView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.home_banner_view, this);
    }

    public void setListener(TaskItemListener listener) {
        if (null != listener) {
            mListener = listener;
            findViewById(R.id.top_more).setOnClickListener(this);
            findViewById(R.id.item_1).setOnClickListener(this);
            findViewById(R.id.item_2).setOnClickListener(this);
            findViewById(R.id.item_3).setOnClickListener(this);
            findViewById(R.id.item_4).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_more:
                mListener.onMore();
                break;
            case R.id.item_1:
                mListener.onItemClick(0);
                break;
            case R.id.item_2:
                mListener.onItemClick(1);
                break;
            case R.id.item_3:
                mListener.onItemClick(2);
                break;
            case R.id.item_4:
                mListener.onItemClick(3);
                break;
        }
    }

    public interface TaskItemListener {

        void onMore();

        void onItemClick(int i);
    }
}
