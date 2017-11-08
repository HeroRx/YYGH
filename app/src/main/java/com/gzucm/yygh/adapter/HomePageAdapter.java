package com.gzucm.yygh.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 主页面的导航栏
 * Created by Administrator on 2017/10/28 0028.
 */

public class HomePageAdapter extends FragmentPagerAdapter {
    //内容Fragment的集合
    private ArrayList<Fragment> mfragments;
    public HomePageAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.mfragments = fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return mfragments.get(position);
    }
    @Override
    public int getCount() {
        return mfragments.size();
    }
}
