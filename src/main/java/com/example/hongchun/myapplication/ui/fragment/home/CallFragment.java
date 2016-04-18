package com.example.hongchun.myapplication.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.fragment.BaseFragment;
import com.example.hongchun.myapplication.ui.view.PullRefreshLayout;
import com.example.hongchun.myapplication.ui.view.PullRefreshView;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by TianHongChun on 2016/4/5.
 */
@ContentView(R.layout.fragment_call_layout)
public class CallFragment extends BaseFragment {


    @ViewInject(R.id.pullview_textView)
    PullRefreshView pullRefreshView;

    @ViewInject(R.id.pullRefreshLayout)
    PullRefreshLayout pullRefreshLayout;

    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                pullRefreshLayout.setRefresh(true);
            }
        });
    }

    @Override
    public void initEven(View view, @Nullable Bundle savedInstanceState) {
        pullRefreshLayout.setOnPullToRefreshListener(new PullRefreshLayout.OnPullToRefreshListener() {
            @Override
            public void onRefreshDown() {
                pullRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullRefreshLayout.setRefresh(false);
                    }
                },2000);

            }

            @Override
            public void onRefreshUp() {

            }
        });
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {

    }
}
