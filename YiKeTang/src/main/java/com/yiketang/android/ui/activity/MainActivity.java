package com.yiketang.android.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.yiketang.android.R;
import com.yiketang.android.base.ToolbarActivity;
import com.yiketang.android.ui.adapter.MainPagerAdapter;
import com.yiketang.android.ui.view.TabCustomView;

/**
 * Created by Xiao.Se on 2016/5/23.
 * 主页
 */
public class MainActivity extends ToolbarActivity {

    public static final int COUNT = 4;
    public static int[] drawables = {R.drawable.icon_demo, R.drawable.icon_demo, R.drawable.icon_demo, R.drawable.icon_demo,};
    public static String[] mTexts;

    public ViewPager mViewPager;

    @Override
    protected int getContentView() {
        return R.layout.main_layout;
    }

    @Override
    protected void initView() {
        setTitle("2xx");
        initTab();
    }

    @Override
    protected void createPresenter() {

    }

    private void initTab() {
        mViewPager = findView(R.id.main_pager);
        TabLayout mTable = findView(R.id.main_TL);
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        //保存4个Fragment状态，以免销毁影响用户体验
        mViewPager.setOffscreenPageLimit(COUNT);
        mTable.setupWithViewPager(mViewPager);

        mTable.removeAllTabs();
        mTexts = getResources().getStringArray(R.array.main_tab_title);
        for (int i = 0; i < COUNT; i++) {
            TabCustomView mTab = new TabCustomView(this);
            mTab.setIcon(drawables[i]);
            mTab.setText(mTexts[i]);
            mTable.addTab(mTable.newTab().setCustomView(mTab));
        }
    }
}
