package com.yiketang.android.base;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Xiao.Se on 2016/5/26.
 * ..
 */
public class BaseReHolder extends RecyclerView.ViewHolder {
    private SparseArrayCompat<View> mViews;
    private View mContentView;

    public BaseReHolder(View itemView) {
        super(itemView);
        this.mContentView = itemView;
        this.mViews = new SparseArrayCompat<>();
    }

    private <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);

        if (null == view) {
            view = mContentView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }

    public BaseReHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }
}
