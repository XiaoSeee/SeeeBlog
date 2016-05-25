package com.yiketang.android.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by WuXiang on 2016/5/25.
 * ..
 */
public abstract class BaseReAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private OnItemClickListener mListener;
    public List<T> mData;
    public Context mContext;

    public void setOnItemClickLitener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public BaseReAdapter(List<T> data, Context context) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        final T item = getItem(position);
        bindItemData(holder, item, position);
        //如果设置了回调，则设置点击事件
        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    protected abstract void bindItemData(VH viewHolder, T data, int position);

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public T getItem(int position) {
        position = Math.max(0, position);
        return mData.get(position);
    }
}
