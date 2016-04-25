package com.example.hongchun.myapplication.ui.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.fragment.BaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.lang.reflect.Field;

/**
 * Created by TianHongChun on 2016/4/25.
 * 通讯录
 */
@ContentView(R.layout.fragment_contact_layout)
public class ContactFragment extends BaseFragment{

    Context context;
    public static final int FRAGMENT_PHONE=1;
    public static final int FRAGMENT_NATIVE=2;

    @ViewInject(R.id.framelayout_contact)
    FrameLayout frameLayout;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        try {
//            Field childFragmentManager = Fragment.class
//                    .getDeclaredField("mChildFragmentManager");
//            childFragmentManager.setAccessible(true);
//            childFragmentManager.set(this, null);
//
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        getFragmentManager().beginTransaction().replace(R.id.framelayout_contact, new ContactPersonFragment(),"ContactPersonFragment").commit();
    }

    @Override
    public void initEven(View view, @Nullable Bundle savedInstanceState) {

    }

    public void setShowFragment(int index){
        FragmentManager fragmentManager=getChildFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        if(index==FRAGMENT_PHONE){
            fragmentTransaction.replace(R.id.framelayout_contact, new ContactPersonFragment(), "ContactPersonFragment").commit();
        }else if(index==FRAGMENT_NATIVE){
            fragmentTransaction.replace(R.id.framelayout_contact, new ContactNativeFragment(),"ContactNativeFragment").commit();
        }
    }

}
