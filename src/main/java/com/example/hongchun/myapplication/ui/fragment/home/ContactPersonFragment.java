package com.example.hongchun.myapplication.ui.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.data.pojo.ContactPersonPojo;
import com.example.hongchun.myapplication.interfacem.implementsm.ImpOnTouchAssortListener;
import com.example.hongchun.myapplication.ui.adapter.ContactPersonRecyclerAdapter;
import com.example.hongchun.myapplication.ui.fragment.BaseFragment;
import com.example.hongchun.myapplication.ui.view.AlphabetView;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TianHongChun on 2016/4/5.
 * 本地通讯录
 */
@ContentView(R.layout.fragment_history_layout)
public class ContactPersonFragment extends BaseFragment   {

    Context context;

    @ViewInject(R.id.alphabetView)
    AlphabetView alphabetView;

    @ViewInject(R.id.recyclerView)
    RecyclerView recyclerView;

    ContactPersonRecyclerAdapter mAdapter;
    List<ContactPersonPojo> contactPersonPojoList;

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
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        contactPersonPojoList=new ArrayList<>();
        contactPersonPojoList.addAll(getTestData());
        mAdapter=new ContactPersonRecyclerAdapter(context,contactPersonPojoList);
        recyclerView.setAdapter(mAdapter);

    }
    @Override
    public void initEven(View view, @Nullable Bundle savedInstanceState) {
        alphabetView.setOnTouchAssortListener(new ImpOnTouchAssortListener(context));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();

                if(newState==RecyclerView.SCROLL_STATE_IDLE){
                   int last= layoutManager.findLastCompletelyVisibleItemPosition();
                    int count=layoutManager.getItemCount();
                    if(last==count-1){
                        Toast.makeText(context,"滚动到底部了",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }





    private List<ContactPersonPojo> getTestData(){
        List<ContactPersonPojo> list=new ArrayList<>();
        for (int i=0;i<20;i++){
            ContactPersonPojo personPojo=new ContactPersonPojo();
            personPojo.setPersonName("张晓明:"+i);
            personPojo.setPersonPhone("1234123:"+i);
            list.add(personPojo);
        }
        return list;
    }
}
