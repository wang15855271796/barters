package com.barter.hyl.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/1
 */
public class HylViewPagerCouponAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private List<String> stringList;

    public HylViewPagerCouponAdapter(FragmentManager fm, List<Fragment> list, List<String> stringList) {

        super(fm);
        this.list=list;
        this.stringList=stringList;

    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }
}
