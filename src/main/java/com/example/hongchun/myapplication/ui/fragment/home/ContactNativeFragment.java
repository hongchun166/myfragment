package com.example.hongchun.myapplication.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.data.dao.MContactPersonDao;
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
 * Created by TianHongChun on 2016/4/25.
 */
@ContentView(R.layout.fragment_contactperson_layout)
public class ContactNativeFragment extends BaseFragment {

    Context context;

    @ViewInject(R.id.alphabetView)
    AlphabetView alphabetView;

    @ViewInject(R.id.recyclerView)
    PinnedHeaderListView pinnedHeaderListView;

    List<ContactPersonPojo> mData;
    ContactPersonListViewAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        mData=new ArrayList<>();
        mAdapter=new ContactPersonListViewAdapter(context,mData);
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
    private void initData(){
        List<ContactPersonPojo> personPojoList= MContactPersonDao.getInstant().selectAll();
        mData.clear();
        mData.addAll(personPojoList);
        CollectionsUtil.sortContactPerson(mData);
        mAdapter.notifyDataSetChanged();
    }
}
