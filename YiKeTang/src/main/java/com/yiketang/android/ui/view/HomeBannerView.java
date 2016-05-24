package com.yiketang.android.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.yiketang.android.R;

/**
 * Created by WuXiang on 2016/5/23.
 * ..
 */
public class HomeBannerView extends RelativeLayout {

    public HomeBannerView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.home_banner_view, this);
    }
}
