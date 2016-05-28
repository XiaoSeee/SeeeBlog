package com.yiketang.android.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiketang.android.R;

/**
 * Created by Xiao.Se on 2016/5/28.
 * ..
 */
public class SettingItemView extends RelativeLayout {

    private String mTitle;
    private TextView title_tv;
    private Drawable leftIcon, rightIcon;
    private ImageView left_iv, right_iv;

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                R.styleable.SettingItemView, defStyleAttr, 0);
        mTitle = a.getString(R.styleable.SettingItemView_settingTitle);
        leftIcon = a.getDrawable(R.styleable.SettingItemView_leftIcon);
        rightIcon = a.getDrawable(R.styleable.SettingItemView_rightIcon);
        a.recycle();
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.setting_item_layout, this);
        title_tv = (TextView) findViewById(R.id.title_tv);
        if (null != mTitle && !mTitle.isEmpty()) {
            title_tv.setText(mTitle);
        }
        left_iv = (ImageView) findViewById(R.id.left_iv);
        if (null != leftIcon) {
            left_iv.setImageDrawable(leftIcon);
        }
        right_iv = (ImageView) findViewById(R.id.right_iv);
        if (null != rightIcon) {
            right_iv.setImageDrawable(rightIcon);
        }
    }
}
