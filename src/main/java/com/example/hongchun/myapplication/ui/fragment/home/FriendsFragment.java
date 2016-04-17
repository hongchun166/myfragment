package com.example.hongchun.myapplication.ui.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.hongchun.myapplication.data.dao.ContactsPersonDao;
import com.example.hongchun.myapplication.data.pojo.ContactPersonPojo;
import com.example.hongchun.myapplication.interfacem.implementsm.ImpOnTouchAssortListener;
import com.example.hongchun.myapplication.ui.adapter.ContactPersonRecyclerAdapter;
import com.example.hongchun.myapplication.ui.fragment.BaseFragment;
import com.example.hongchun.myapplication.ui.view.AlphabetView;
import com.example.hongchun.myapplication.util.CollectionsUtil;
import com.google.gson.Gson;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by TianHongChun on 2016/4/5.
 * 本地通讯录
 */
@ContentView(R.layout.fragment_history_layout)
public class FriendsFragment extends BaseFragment   {

    Context context;

    @ViewInject(R.id.alphabetView)
    AlphabetView alphabetView;

    @ViewInject(R.id.recyclerView)
    RecyclerView recyclerView;

    @ViewInject(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

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
        alphabetView.setOnTouchAssortListener(new ImpOnTouchAssortListener(context) {
            @Override
            public void onTouchAssortChanged(String s) {
                super.onTouchAssortChanged(s);
                int position = mAdapter.getPositionForSection(s.charAt(0));
                recyclerView.getLayoutManager().scrollToPosition(position);

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int last = layoutManager.findLastCompletelyVisibleItemPosition();
                    int count = layoutManager.getItemCount();
                    if (last == count - 1) {
                        Toast.makeText(context, "滚动到底部了", Toast.LENGTH_SHORT);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

   private void initData(){
       List<ContactPersonPojo> personPojoList= ContactsPersonDao.getContactsPersonList(context);
       contactPersonPojoList.clear();
       contactPersonPojoList.addAll(personPojoList);
       mAdapter.notifyDataSetChanged();
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




    private List<ContactPersonPojo> getTestData(){
        List<ContactPersonPojo> list=new ArrayList<>();

        ContactPersonPojo personPojo1=new ContactPersonPojo("1","张晓明","123412311","");
        list.add(personPojo1);
        ContactPersonPojo personPojo2=new ContactPersonPojo("1","张三","123412312","");
        list.add(personPojo2);
        ContactPersonPojo personPojo3=new ContactPersonPojo("1","李四","1234123","");
        list.add(personPojo3);
        ContactPersonPojo personPojo4=new ContactPersonPojo("1","王五","1234123","");
        list.add(personPojo4);
        ContactPersonPojo personPojo5=new ContactPersonPojo("1","张大胆","1234123","");
        list.add(personPojo5);
        ContactPersonPojo personPojo6=new ContactPersonPojo("1","王二","1234123","");
        list.add(personPojo6);
        ContactPersonPojo personPojo7=new ContactPersonPojo("1","妮娜","1234123","");
        list.add(personPojo7);
        ContactPersonPojo personPojo8=new ContactPersonPojo("1","刘立","1234123","");
        list.add(personPojo8);
        ContactPersonPojo personPojo9=new ContactPersonPojo("1","刘建","1234123","");
        list.add(personPojo9);
        ContactPersonPojo personPojo10=new ContactPersonPojo("1","黄明","1234123","");
        list.add(personPojo10);
        ContactPersonPojo personPojo11=new ContactPersonPojo("1","黄剑","1234123","");
        list.add(personPojo11);
        ContactPersonPojo personPojo12=new ContactPersonPojo("1","黄散","1234123","");
        list.add(personPojo12);
        ContactPersonPojo personPojo13=new ContactPersonPojo("1","王1","1234123","");
        list.add(personPojo13);
        ContactPersonPojo personPojo14=new ContactPersonPojo("1","王12","1234123","");
        list.add(personPojo14);
        ContactPersonPojo personPojo15=new ContactPersonPojo("1","张晓明1","1234123","");
        list.add(personPojo15);
        ContactPersonPojo personPojo16=new ContactPersonPojo("1","张晓明2","1234123","");
        list.add(personPojo16);
        ContactPersonPojo personPojo17=new ContactPersonPojo("1","阿萨","1234123","");
        list.add(personPojo17);
        ContactPersonPojo personPojo18=new ContactPersonPojo("1","古喝","1234123","");
        list.add(personPojo18);
        ContactPersonPojo personPojo19=new ContactPersonPojo("1","喝彩","1234123","");
        list.add(personPojo19);
        ContactPersonPojo personPojo20=new ContactPersonPojo("1","高达","1234123","");
        list.add(personPojo20);
        ContactPersonPojo personPojo21=new ContactPersonPojo("1","飒斯","1234123","");
        list.add(personPojo21);

        CollectionsUtil.sortContactPerson(list);
        return list;
    }
}
