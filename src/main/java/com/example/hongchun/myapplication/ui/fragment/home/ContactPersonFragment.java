package com.example.hongchun.myapplication.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.data.pojo.ContactPersonPojo;
import com.example.hongchun.myapplication.interfacem.implementsm.ImpOnTouchAssortListener;
import com.example.hongchun.myapplication.ui.adapter.ContactPersonListViewAdapter;
import com.example.hongchun.myapplication.ui.fragment.BaseFragment;
import com.example.hongchun.myapplication.ui.view.AlphabetView;
import com.example.hongchun.myapplication.ui.view.PinnedHeaderListView;
import com.example.hongchun.myapplication.util.CollectionsUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TianHongChun on 2016/4/5.
 */
@ContentView(R.layout.fragment_friends_layout)
public class ContactPersonFragment extends BaseFragment {

    Context context;

    @ViewInject(R.id.alphabetView)
    AlphabetView alphabetView;

    @ViewInject(R.id.recyclerView)
    PinnedHeaderListView pinnedHeaderListView;

    ContactPersonListViewAdapter mAdapter;

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
        ArrayList<ContactPersonPojo> list=new ArrayList<>();
        list.addAll(getTestData());
        mAdapter=new ContactPersonListViewAdapter(context,list);
        pinnedHeaderListView.setAdapter(mAdapter);

    }

    @Override
    public void initEven(View view, @Nullable Bundle savedInstanceState) {
        alphabetView.setOnTouchAssortListener(new ImpOnTouchAssortListener(context, true) {
            @Override
            public void onTouchAssortChanged(String s) {
                super.onTouchAssortChanged(s);
                int position = mAdapter.getPositionForSection(s.charAt(0));
                pinnedHeaderListView.setSelection(position);
            }
        });
    }


    private List<ContactPersonPojo> getTestData(){
        List<ContactPersonPojo> list=new ArrayList<>();
        ContactPersonPojo personPojo1=new ContactPersonPojo("1","张三","123412311","");
        list.add(personPojo1);
        ContactPersonPojo personPojo2=new ContactPersonPojo("1","张四","123412312","");
        list.add(personPojo2);
        ContactPersonPojo personPojo3=new ContactPersonPojo("1","李武","1234123","");
        list.add(personPojo3);
        ContactPersonPojo personPojo4=new ContactPersonPojo("1","李六","1234123","");
        list.add(personPojo4);
        ContactPersonPojo personPojo5=new ContactPersonPojo("1","王二","1234123","");
        list.add(personPojo5);
        ContactPersonPojo personPojo6=new ContactPersonPojo("1","王二","1234123","");
        list.add(personPojo6);
        ContactPersonPojo personPojo7=new ContactPersonPojo("1","王一","1234123","");
        list.add(personPojo7);
        ContactPersonPojo personPojo8=new ContactPersonPojo("1","刘柳","1234123","");
        list.add(personPojo8);
        ContactPersonPojo personPojo9=new ContactPersonPojo("1","刘建","1234123","");
        list.add(personPojo9);
        ContactPersonPojo personPojo10=new ContactPersonPojo("1","黄明","1234123","");
        list.add(personPojo10);
        ContactPersonPojo personPojo11=new ContactPersonPojo("1","黄剑","1234123","");
        list.add(personPojo11);
        ContactPersonPojo personPojo12=new ContactPersonPojo("1","安琪","1234123","");
        list.add(personPojo12);
        ContactPersonPojo personPojo13=new ContactPersonPojo("1","安吉","1234123","");
        list.add(personPojo13);
        ContactPersonPojo personPojo14=new ContactPersonPojo("1","古河","1234123","");
        list.add(personPojo14);
        ContactPersonPojo personPojo15=new ContactPersonPojo("1","股海","1234123","");
        list.add(personPojo15);
        ContactPersonPojo personPojo16=new ContactPersonPojo("1","龙龙","1234123","");
        list.add(personPojo16);
        ContactPersonPojo personPojo17=new ContactPersonPojo("1","龙阿尼","1234123","");
        list.add(personPojo17);
        ContactPersonPojo personPojo18=new ContactPersonPojo("1","汪峰","1234123","");
        list.add(personPojo18);
        ContactPersonPojo personPojo19=new ContactPersonPojo("1","汪武","1234123","");
        list.add(personPojo19);
        ContactPersonPojo personPojo20=new ContactPersonPojo("1","高斯","1234123","");
        list.add(personPojo20);
        ContactPersonPojo personPojo21=new ContactPersonPojo("1","高米","1234123","");
        list.add(personPojo21);

        CollectionsUtil.sortContactPersonTest(list);
        return list;
    }
}
