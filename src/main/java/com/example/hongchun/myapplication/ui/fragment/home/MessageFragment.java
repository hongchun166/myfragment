package com.example.hongchun.myapplication.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import org.xutils.view.annotation.ViewInject;

/**
 * Created by TianHongChun on 2016/4/5.
 */
@ContentView(R.layout.fragment_message_layout)
public class MessageFragment extends BaseFragment {


    Context context;

    @ViewInject(R.id.listView)
    ListView  listView;

    @ViewInject(R.id.textView_group)
    TextView textViewGroup;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.d("MessageFragment=onCreateView==");
    }


    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
            View view1=LayoutInflater.from(context).inflate(R.layout.contact_person_group_layout,null);
            View view2=LayoutInflater.from(context).inflate(R.layout.contact_person_group_layout,null);
            listView.addHeaderView(view1);
            listView.addHeaderView(view2);
    }

    @Override
    public void initEven(View view, @Nullable Bundle savedInstanceState) {
        LogUtil.d("initEven=onCreateView==");
        String[]   strs= new String[20];
        for (int i = 0; i < 20; i++) {
            strs[i] = "data-----" + i;
        }
        listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, strs));
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem <1) {
                    textViewGroup.setVisibility(View.GONE);
                }else {
                    textViewGroup.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
