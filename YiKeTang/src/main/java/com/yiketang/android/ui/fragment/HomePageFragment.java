package com.yiketang.android.ui.fragment;

import android.widget.LinearLayout;

import com.yiketang.android.R;
import com.yiketang.android.base.BaseFragment;
import com.yiketang.android.presenter.contract.HomePageContract;
import com.yiketang.android.ui.view.HomeBannerView;
import com.yiketang.android.util.LogUtil;

/**
 * Created by Xiao.Se on 2016/5/23.
 * ..
 */
public class HomePageFragment extends BaseFragment implements HomePageContract.View {

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    public void setPresenter(HomePageContract.Presenter presenter) {

    }


    @Override
    protected int getContentView() {
        return R.layout.home_page_layout;
    }

    @Override
    protected void initView() {
        LinearLayout ll = findView(R.id.scroll);
        HomeBannerView view = new HomeBannerView(mContext);
        view.setListener(new HomeBannerView.TaskItemListener() {

            @Override
            public void onMore() {
                LogUtil.d("more");
            }

            @Override
            public void onItemClick(int i) {
                LogUtil.d("onItemClick = " + i);
            }
        });
        ll.addView(view);
        ll.addView(new HomeBannerView(mContext));
        ll.addView(new HomeBannerView(mContext));
    }

    @Override
    protected void createPresenter() {

    }
}
