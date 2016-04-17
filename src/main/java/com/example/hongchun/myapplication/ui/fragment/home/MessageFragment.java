package com.example.hongchun.myapplication.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.fragment.BaseFragment;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by TianHongChun on 2016/4/5.
 */
@ContentView(R.layout.fragment_message_layout)
public class MessageFragment extends BaseFragment {


    Context context;

    @ViewInject(R.id.listView)
    ListView  listView;

    @ViewInject(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void initEven(View view, @Nullable Bundle savedInstanceState) {
        LogUtil.d("initEven=onCreateView==");
        String[]   strs= new String[30];
        for (int i = 0; i < 30; i++) {
            strs[i] = "data-----" + i;
        }
        listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, strs));

    }

    @Event(value = R.id.swipeRefreshLayout,type= SwipeRefreshLayout.OnRefreshListener.class)
    private void onRefreshDataEvent() {
        hideSwipeRefreshIcon();
    }

    /**
     * 显示刷新按钮
     */
    public void showSwipeRefreshIcon(){
        //显示加载图标
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    /**
     * 隐藏刷新按钮
     */
    public void hideSwipeRefreshIcon(){
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //关闭加载图标
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

}
