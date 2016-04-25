package com.example.hongchun.myapplication.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

/**
 * Created by TianHongChun on 2016/4/6.
 */
public abstract class BaseFragment extends Fragment {
    public  abstract void initView(View view, @Nullable Bundle savedInstanceState);
   public abstract void initEven(View view, @Nullable Bundle savedInstanceState);


    private boolean injected = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        injected = true;
        return  x.view().inject(this,inflater,container);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
        initView(view,savedInstanceState);
        initEven(view,savedInstanceState);
    }
}
