package com.example.hongchun.myapplication.ui.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.data.pojo.ContactPersonPojo;
import com.example.hongchun.myapplication.interfacem.implementsm.ImpOnTouchAssortListener;
import com.example.hongchun.myapplication.ui.adapter.FriendsRecyclerAdapter;
import com.example.hongchun.myapplication.ui.fragment.BaseFragment;
import com.example.hongchun.myapplication.ui.view.AlphabetView;
import com.example.hongchun.myapplication.util.CollectionsUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TianHongChun on 2016/4/5.
 * 本地通讯录
 */
@ContentView(R.layout.fragment_friends_layout)
public class FriendsFragment extends BaseFragment   {

    Context context;
    Activity activity;

    @ViewInject(R.id.alphabetView)
    AlphabetView alphabetView;

    @ViewInject(R.id.recyclerView)
    RecyclerView recyclerView;


    FriendsRecyclerAdapter mAdapter;
    List<ContactPersonPojo> mData;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                   final List<ContactPersonPojo> pojoList=new ArrayList<ContactPersonPojo>();
                    for (String username:usernames){
                        ContactPersonPojo personPojo=new ContactPersonPojo();
                        personPojo.setName(username);
                        pojoList.add(personPojo);
                    }
                    CollectionsUtil.sortContactPerson(pojoList);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mData.clear();
                            mData.addAll(pojoList);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mData=new ArrayList<>();
        mData.addAll(getTestData());
        mAdapter=new FriendsRecyclerAdapter(context,mData);
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

   }





    private List<ContactPersonPojo> getTestData(){
        List<ContactPersonPojo> list=new ArrayList<>();
        ContactPersonPojo personPojo1=new ContactPersonPojo(1,"张三","123412311","");
        list.add(personPojo1);
        ContactPersonPojo personPojo2=new ContactPersonPojo(2,"张四","123412312","");
        list.add(personPojo2);
        ContactPersonPojo personPojo3=new ContactPersonPojo(3,"李武","1234123","");
        list.add(personPojo3);
        ContactPersonPojo personPojo4=new ContactPersonPojo(4,"李六","1234123","");
        list.add(personPojo4);
        ContactPersonPojo personPojo5=new ContactPersonPojo(5,"王二","1234123","");
        list.add(personPojo5);
        ContactPersonPojo personPojo6=new ContactPersonPojo(6,"王二","1234123","");
        list.add(personPojo6);
        ContactPersonPojo personPojo7=new ContactPersonPojo(7,"王一","1234123","");
        list.add(personPojo7);
        ContactPersonPojo personPojo8=new ContactPersonPojo(8,"刘柳","1234123","");
        list.add(personPojo8);
        ContactPersonPojo personPojo9=new ContactPersonPojo(9,"刘建","1234123","");
        list.add(personPojo9);
        ContactPersonPojo personPojo10=new ContactPersonPojo(10,"黄明","1234123","");
        list.add(personPojo10);
        ContactPersonPojo personPojo11=new ContactPersonPojo(11,"黄剑","1234123","");
        list.add(personPojo11);
        ContactPersonPojo personPojo12=new ContactPersonPojo(12,"安琪","1234123","");
        list.add(personPojo12);
        ContactPersonPojo personPojo13=new ContactPersonPojo(13,"安吉","1234123","");
        list.add(personPojo13);
        ContactPersonPojo personPojo14=new ContactPersonPojo(14,"古河","1234123","");
        list.add(personPojo14);
        ContactPersonPojo personPojo15=new ContactPersonPojo(15,"股海","1234123","");
        list.add(personPojo15);
        ContactPersonPojo personPojo16=new ContactPersonPojo(16,"龙龙","1234123","");
        list.add(personPojo16);
        ContactPersonPojo personPojo17=new ContactPersonPojo(17,"龙阿尼","1234123","");
        list.add(personPojo17);
        ContactPersonPojo personPojo18=new ContactPersonPojo(18,"汪峰","1234123","");
        list.add(personPojo18);
        ContactPersonPojo personPojo19=new ContactPersonPojo(19,"汪武","1234123","");
        list.add(personPojo19);
        ContactPersonPojo personPojo20=new ContactPersonPojo(20,"高斯","1234123","");
        list.add(personPojo20);
        ContactPersonPojo personPojo21=new ContactPersonPojo(21,"高米","1234123","");
        list.add(personPojo21);

        CollectionsUtil.sortContactPerson(list);
        return list;
    }
}
