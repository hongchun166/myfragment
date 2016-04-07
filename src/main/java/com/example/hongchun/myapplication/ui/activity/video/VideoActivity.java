package com.example.hongchun.myapplication.ui.activity.video;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.activity.BaseNormActivity;
import com.example.hongchun.myapplication.ui.adapter.MFragmentPagerAdapter;
import com.example.hongchun.myapplication.ui.fragment.main.Tab1Fragment;
import com.example.hongchun.myapplication.ui.fragment.main.Tab2Fragment;
import com.example.hongchun.myapplication.ui.fragment.main.Tab3Fragment;
import com.example.hongchun.myapplication.ui.fragment.video.MediaplayerVideoFragment;
import com.example.hongchun.myapplication.ui.fragment.video.SystemVideoFragment;
import com.example.hongchun.myapplication.ui.fragment.video.VideoViewVideoFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TianHongChun on 2016/4/7.
 */
@ContentView(R.layout.activity_video)
public class VideoActivity extends BaseNormActivity {

    @ViewInject(R.id.viewpage)
    ViewPager mViewPager;

    @ViewInject(R.id.tab_layout)
    private TabLayout mTabLayout;

    private List<String> titles;
    private List<Fragment> fragments;
    private MFragmentPagerAdapter mFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    private void initView(){

        fragments = new ArrayList<>();
        Fragment videoViewVideoFragment = new VideoViewVideoFragment();
        Fragment mediaplayerVideoFragment = new MediaplayerVideoFragment();
        Fragment systemVideoFragment = new SystemVideoFragment();
        fragments.add(videoViewVideoFragment);
        fragments.add(mediaplayerVideoFragment);
        fragments.add(systemVideoFragment);

        titles = new ArrayList<>();
        titles.add("videoview播放");
        titles.add("mediaplayer播放");
        titles.add("系统播放");

        mFragmentPagerAdapter = new MFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mFragmentPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }


}
