package com.yiketang.android.ui.adapter;

import android.content.Context;
import android.view.View;

import com.yiketang.android.R;
import com.yiketang.android.base.BaseReHolder;
import com.yiketang.android.base.SingleReAdapter;
import com.yiketang.android.util.LogUtil;

import java.util.List;

/**
 * Created by Xiao.Se on 2016/5/26.
 * ..
 */
public class MajorListAdapter extends SingleReAdapter<String> {
    protected int mCurrentSelect = -1;

    public MajorListAdapter(List<String> data, Context context) {
        super(data, context);
    }

    @Override
    protected int getContentView() {
        return R.layout.course_item_layout;
    }

    @Override
    protected void setListener(final BaseReHolder holder, final int position) {
        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(holder.itemView, position);
                    setCurrentSelect(position);
                }
            });
        }
    }

    @Override
    public void bindItemData(BaseReHolder viewHolder, String data, int position) {
        LogUtil.d("bindItemData : position = " + position);
        viewHolder.setText(R.id.text_view, data);
        if (position == mCurrentSelect) {
            viewHolder.setRlBackgroundColor(R.id.root_rl, mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            viewHolder.setRlBackgroundColor(R.id.root_rl, mContext.getResources().getColor(R.color.background));
        }
    }

    public void setCurrentSelect(int currentSelect) {
        notifyItemChanged(mCurrentSelect);
        this.mCurrentSelect = currentSelect;
        notifyItemChanged(mCurrentSelect);
    }

    public boolean isSelectDate() {
        return mCurrentSelect >= 0;
    }

    public int getCurrentSelect() {
        return mCurrentSelect;
    }
}
