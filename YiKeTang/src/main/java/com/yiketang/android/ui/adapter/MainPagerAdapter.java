package com.yiketang.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yiketang.android.ui.activity.MainActivity;
import com.yiketang.android.ui.fragment.CoursePageFragment;
import com.yiketang.android.ui.fragment.HomePageFragment;
import com.yiketang.android.ui.fragment.MyPageFragment;


/**
 * Created by Xiang on 2015/9/9.
 * MainPagerAdapter
 * 测试提交
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomePageFragment.newInstance();
            case 1:
                return HomePageFragment.newInstance();
            case 2:
                return CoursePageFragment.newInstance();
            case 3:
                return MyPageFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return MainActivity.COUNT;
    }
}
