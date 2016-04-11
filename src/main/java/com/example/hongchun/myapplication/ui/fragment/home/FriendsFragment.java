package com.example.hongchun.myapplication.ui.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.interfacem.implementsm.ImpOnTouchAssortListener;
import com.example.hongchun.myapplication.ui.fragment.BaseFragment;
import com.example.hongchun.myapplication.ui.view.AlphabetView;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by TianHongChun on 2016/4/5.
 */
@ContentView(R.layout.fragment_friends_layout)
public class FriendsFragment extends BaseFragment {

    Context context;

    @ViewInject(R.id.alphabetView)
    AlphabetView alphabetView;

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
        alphabetView.setOnTouchAssortListener(new ImpOnTouchAssortListener(context,true));
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {

    }

}
