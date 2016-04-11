package com.example.hongchun.myapplication.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jjand on 2015/12/14.
 * mainactivity fragmentViewpage adapter
 */
public class MFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> fragmentTitles;
    public MFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> fragmentTitles) {
        super(fm);
        this.fragments = fragments;
        this.fragmentTitles = fragmentTitles;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    @Override
    public int getCount() {
        return fragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }
}