package com.yiketang.android.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiketang.android.R;


/**
 * Created by Xiang on 2015/9/9.
 * 自定义底部tab
 */
public class TabCustomView extends FrameLayout {
    private ImageView mImageView;
    private TextView mTextView;

    public TabCustomView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.main_tab_item, this);
        mImageView = (ImageView) findViewById(R.id.main_item_image);
        mTextView = (TextView) findViewById(R.id.main_item_title);
    }

    public void setText(String str) {
        mTextView.setText(str);
    }

    public void setIcon(int resId) {
        mImageView.setImageResource(resId);
    }
}
