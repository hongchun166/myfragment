package com.example.hongchun.myapplication.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.fragment.BaseFragment;
import com.example.hongchun.myapplication.ui.view.PullRefreshLayout2;
import com.example.hongchun.myapplication.ui.view.index.PullRefreshListView;
import com.example.hongchun.myapplication.ui.view.index.PullRefreshTextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by TianHongChun on 2016/4/5.
 */
@ContentView(R.layout.fragment_call_layout)
public class CallFragment extends BaseFragment {


    @ViewInject(R.id.pullrefreshListView)
    PullRefreshListView pullRefreshListView;

    @ViewInject(R.id.pullRefreshLayout)
    PullRefreshLayout2 pullRefreshLayout;

    Context context;

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
    public void initEven(View view, @Nullable Bundle savedInstanceState) {

        pullRefreshLayout.setOnPullToRefreshListener(new PullRefreshLayout2.OnPullToRefreshListener() {
            @Override
            public void onRefreshDown(PullRefreshLayout2 pullRefreshLayout1) {
                pullRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullRefreshLayout.setRefresh(false);
                    }
                },10000);

            }

            @Override
            public void onRefreshUp(PullRefreshLayout2 pullRefreshLayout1) {
                pullRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullRefreshLayout.setRefreshLoad(false);
                    }
                },5000);
            }
        });

        pullRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context,"=======onItemClick=====",Toast.LENGTH_SHORT).show();
            }
        });
        pullRefreshListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context,"======onItemLongClick===",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        String[] strings=new String[30];
        for (int i=0;i<30;i++){
            strings[i]="xiao "+i;
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,strings);
        pullRefreshListView.setAdapter(adapter);
    }



}
