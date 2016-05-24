package com.yiketang.android.ui.fragment;

import android.widget.LinearLayout;

import com.yiketang.android.R;
import com.yiketang.android.base.BaseFragment;
import com.yiketang.android.presenter.contract.HomePageContract;
import com.yiketang.android.ui.view.HomeBannerView;

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
        ll.addView(new HomeBannerView(mContext));
    }

    @Override
    protected void createPresenter() {

    }
}
