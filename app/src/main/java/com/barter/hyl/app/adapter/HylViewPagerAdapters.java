package com.barter.hyl.app.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/26
 */
public class HylViewPagerAdapters extends FragmentPagerAdapter {
    private List<Fragment> list;
    private List<String> stringList;

    public HylViewPagerAdapters(FragmentManager fm, List<Fragment> list, List<String> stringList) {

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
