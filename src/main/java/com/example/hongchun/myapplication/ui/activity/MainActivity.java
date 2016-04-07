package com.example.hongchun.myapplication.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.adapter.MFragmentPagerAdapter;
import com.example.hongchun.myapplication.ui.fragment.main.Tab1Fragment;
import com.example.hongchun.myapplication.ui.fragment.main.Tab2Fragment;
import com.example.hongchun.myapplication.ui.fragment.main.Tab3Fragment;
import com.example.hongchun.myapplication.ui.fragment.main.Tab4Fragment;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseExitActivity {

    @ViewInject(R.id.fab_button)
    private FloatingActionButton fab;

    @ViewInject(R.id.viewpage)
    private ViewPager mViewPager;

    @ViewInject(R.id.tab_layout)
    private TabLayout mTabLayout;

    private List<Fragment> fragments;

    private List<String> titles;

    private MFragmentPagerAdapter mFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewPage();
        initTab();
        initEven();
    }

    private void initViewPage(){
        //初始化fragment
        fragments = new ArrayList<>();
        Fragment tab1Fragment = new Tab1Fragment();
        Fragment tab2Fragment = new Tab2Fragment();
        Fragment tab3Fragment = new Tab3Fragment();
        Fragment tab4Fragment = new Tab4Fragment();
        fragments.add(tab1Fragment);
        fragments.add(tab2Fragment);
        fragments.add(tab3Fragment);
        fragments.add(tab4Fragment);
        //初始化标题
        titles = new ArrayList<>();
        titles.add("tab1");
        titles.add("tab2");
        titles.add("tab3");
        titles.add("tab4");
        mFragmentPagerAdapter = new MFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mFragmentPagerAdapter);
    }
    private void initTab() {
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initEven(){
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    fab.show();
                } else {
                    fab.hide();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Event(R.id.fab_button)
    private void onEvenOnclick(View view){
        switch (view.getId()) {
            case R.id.fab_button:
                Toast.makeText(this,"点击了添加按钮",Toast.LENGTH_SHORT).show();
                break;
            default:
                LogUtil.w(getResources().getString(R.string.DEBUG_EVENT_LISTENER_TIPS));
        }
    }

}
