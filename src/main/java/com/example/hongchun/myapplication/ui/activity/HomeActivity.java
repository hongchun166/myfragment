package com.example.hongchun.myapplication.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.fragment.home.CallFragment;
import com.example.hongchun.myapplication.ui.fragment.home.FriendsFragment;
import com.example.hongchun.myapplication.ui.fragment.home.HistoryFragment;
import com.example.hongchun.myapplication.ui.fragment.home.HomeFragment;
import com.example.hongchun.myapplication.ui.fragment.home.MessageFragment;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by TianHongChun on 2016/4/6.
 */
@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseExitActivity {

    @ViewInject(R.id.radiogroup)
    RadioGroup radioGroup;

    @ViewInject(R.id.framelayout)
    FrameLayout frameLayout;

    @ViewInject(R.id.radiobutton_call)
    RadioButton radioButton_call;

    @ViewInject(R.id.radiobutton_history)
    RadioButton radioButton_history;

    @ViewInject(R.id.radiobutton_home)
    RadioButton radioButton_home;

    @ViewInject(R.id.radiobutton_message)
    RadioButton radioButton_message;

    @ViewInject(R.id.radiobutton_friends)
    RadioButton radioButton_friends;

    @ViewInject(R.id.textview_titlename)
    TextView textView_titleName;

    Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultFragment();
    }

    private void setDefaultFragment(){
        Fragment fragment=new HomeFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.framelayout, fragment, "HomeFragment");
        transaction.commit();
        tempFragment=fragment;
        setTitleName(getResources().getString(R.string.string_home_tab_home));
    }

    @Event(value = {R.id.radiogroup},type = RadioGroup.OnCheckedChangeListener.class)
    private void onEvenOnCheckedChanged(RadioGroup group, int checkedId){
        FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
                if(tempFragment!=null){
                    transaction.hide(tempFragment);
                }
            Fragment fragment=null;
            int framelayoutId=R.id.framelayout;

                switch (checkedId){
                    case R.id.radiobutton_friends:
                        fragment=fragmentManager.findFragmentByTag("FriendsFragment");
                        if(fragment!=null && fragment.isAdded()){
                            transaction.show(fragment);
                        }else {
                            fragment=new FriendsFragment();
                            transaction.add(framelayoutId,fragment, "FriendsFragment");
                        }
                        setTitleName(getResources().getString(R.string.string_home_tab_friends));
                        break;
                    case R.id.radiobutton_call:
                        fragment=fragmentManager.findFragmentByTag("CallFragment");
                        if(fragment!=null && fragment.isAdded()){
                            transaction.show(fragment);
                        }else {
                            fragment=new CallFragment();
                            transaction.add(framelayoutId,fragment, "CallFragment");
                        }
                        setTitleName(getResources().getString(R.string.string_home_tab_call));
                        break;
                    case R.id.radiobutton_home:
                        fragment=fragmentManager.findFragmentByTag("HomeFragment");
                        if(fragment!=null && fragment.isAdded()){
                            transaction.show(fragment);
                        }else {
                            fragment=new HomeFragment();
                            transaction.add(framelayoutId,fragment, "HomeFragment");
                        }
                        setTitleName(getResources().getString(R.string.string_home_tab_home));
                        break;
                    case R.id.radiobutton_history:
                        fragment=fragmentManager.findFragmentByTag("HistoryFragment");
                        if(fragment!=null && fragment.isAdded()){
                            transaction.show(fragment);
                        }else {
                            fragment=new HistoryFragment();
                            transaction.add(framelayoutId,fragment, "HistoryFragment");
                        }
                        setTitleName(getResources().getString(R.string.string_home_tab_history));
                        break;
                    case R.id.radiobutton_message:
                        fragment=fragmentManager.findFragmentByTag("MessageFragment");
                        if(fragment!=null && fragment.isAdded()){
                            transaction.show(fragment);
                        }else {
                            fragment=new MessageFragment();
                            transaction.add(framelayoutId,fragment, "MessageFragment");
                        }
                        setTitleName(getResources().getString(R.string.string_home_tab_message));
                        break;
                }
            transaction.commit();
            tempFragment=fragment;
    }

    public void setTitleName(String titleName){
        textView_titleName.setText(titleName);
    }

}
