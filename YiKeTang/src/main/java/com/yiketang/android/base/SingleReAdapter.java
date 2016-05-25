package com.yiketang.android.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by WuXiang on 2016/5/25.
 * ..
 */
public abstract class SingleReAdapter<T> extends BaseReAdapter<T, BaseReHolder> {


    public SingleReAdapter(List<T> data, Context context) {
        super(data, context);
    }

    @Override
    public BaseReHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseReHolder(LayoutInflater.from(mContext).inflate(getContentView(), parent, false));
    }

    protected abstract int getContentView();


}
