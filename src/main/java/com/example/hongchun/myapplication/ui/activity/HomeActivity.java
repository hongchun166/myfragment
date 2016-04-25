package com.example.hongchun.myapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.activity.zxing.activity.CaptureActivity;
import com.example.hongchun.myapplication.ui.activity.zxing.activity.EncodingActivity;
import com.example.hongchun.myapplication.ui.fragment.home.CallFragment;
import com.example.hongchun.myapplication.ui.fragment.home.ContactFragment;
import com.example.hongchun.myapplication.ui.fragment.home.ContactPersonFragment;
import com.example.hongchun.myapplication.ui.fragment.home.FriendsFragment;
import com.example.hongchun.myapplication.ui.fragment.home.HomeFragment;
import com.example.hongchun.myapplication.ui.fragment.home.MessageFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by TianHongChun on 2016/4/6.
 */
@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseExitActivity {

    @ViewInject(R.id.toolbar)
    private Toolbar mToolBar;

    @ViewInject(R.id.textview_titlename)
    TextView textViewTitlename;

    @ViewInject(R.id.radiogroup)
    RadioGroup radioGroup;

    @ViewInject(R.id.framelayout)
    FrameLayout frameLayout;

    @ViewInject(R.id.radiobutton_call)
    RadioButton radioButton_call;

    @ViewInject(R.id.radiobutton_ContactPerson)
    RadioButton radioButtonContactPerson;

    @ViewInject(R.id.radiobutton_home)
    RadioButton radioButton_home;

    @ViewInject(R.id.radiobutton_message)
    RadioButton radioButton_message;

    @ViewInject(R.id.radiobutton_friends)
    RadioButton radioButton_friends;

    //通讯录模式按钮
    @ViewInject(R.id.radiogroup_contact)
    RadioGroup radioGroupContact;

    @ViewInject(R.id.radioButton_phone)
    RadioButton radioButtonPhone;

    @ViewInject(R.id.radioButton_native)
    RadioButton radioButtonNative;


    Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar(mToolBar);
        setContactFragmentShowHideRadioButton(false);

        setDefaultFragment();

    }
    //菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //noinspection SimplifiableIfStatement
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }else if(item.getItemId()==R.id.action_code){
            Intent intent=new Intent(this, CaptureActivity.class);
            startActivityForResult(intent,100);
        }else if(item.getItemId()==R.id.action_myselfcode){
            Intent intent=new Intent(this, EncodingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDefaultFragment(){


        Fragment fragment=new HomeFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.framelayout, fragment, "HomeFragment");
        transaction.commit();
        tempFragment=fragment;
        initToolBar(mToolBar, textViewTitlename, getResources().getString(R.string.string_home_tab_home));
    }

    @Event(value = {R.id.radiogroup,R.id.radiogroup_contact},type = RadioGroup.OnCheckedChangeListener.class)
    private void onEvenOnCheckedChanged(RadioGroup group, int checkedId){

        if(group.getId()==R.id.radiogroup_contact){
            // 判断点击的是不是通讯录中的radiobutton
            if(checkedId==R.id.radioButton_phone){
                ((ContactFragment)tempFragment).setShowFragment(ContactFragment.FRAGMENT_PHONE);
            }else if(checkedId==R.id.radioButton_native){
                ((ContactFragment)tempFragment).setShowFragment(ContactFragment.FRAGMENT_NATIVE);
            }
            return;
        }
        FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
                if(tempFragment!=null){
                    transaction.hide(tempFragment);
                }
            int framelayoutId=R.id.framelayout;

                switch (checkedId){
                    case R.id.radiobutton_ContactPerson:
                        tempFragment=fragmentManager.findFragmentByTag("ContactFragment");
                        if(tempFragment!=null && tempFragment.isAdded()){
                            transaction.show(tempFragment);
                        }else {
                            tempFragment=new ContactFragment();
                            transaction.add(framelayoutId,tempFragment, "ContactFragment");
                        }
                        setToolBarTitle(textViewTitlename, getResources().getString(R.string.string_home_tab_contact));
                        setContactFragmentShowHideRadioButton(true);

                        break;
                    case R.id.radiobutton_call:
                        tempFragment=fragmentManager.findFragmentByTag("CallFragment");
                        if(tempFragment!=null && tempFragment.isAdded()){
                            transaction.show(tempFragment);
                        }else {
                            tempFragment=new CallFragment();
                            transaction.add(framelayoutId,tempFragment, "CallFragment");
                        }
                        setToolBarTitle(textViewTitlename,getResources().getString(R.string.string_home_tab_call));
                        setContactFragmentShowHideRadioButton(false);
                        break;
                    case R.id.radiobutton_home:
                        tempFragment=fragmentManager.findFragmentByTag("HomeFragment");
                        if(tempFragment!=null && tempFragment.isAdded()){
                            transaction.show(tempFragment);
                        }else {
                            tempFragment=new HomeFragment();
                            transaction.add(framelayoutId,tempFragment, "HomeFragment");
                        }
                        setToolBarTitle(textViewTitlename,getResources().getString(R.string.string_home_tab_home));
                        setContactFragmentShowHideRadioButton(false);
                        break;
                    case R.id.radiobutton_friends:
                        tempFragment=fragmentManager.findFragmentByTag("FriendsFragment");
                        if(tempFragment!=null && tempFragment.isAdded()){
                            transaction.show(tempFragment);
                        }else {
                            tempFragment=new FriendsFragment();
                            transaction.add(framelayoutId,tempFragment, "FriendsFragment");
                        }
                        setToolBarTitle(textViewTitlename,getResources().getString(R.string.string_home_tab_friends));
                        setContactFragmentShowHideRadioButton(false);
                        break;
                    case R.id.radiobutton_message:
                        tempFragment=fragmentManager.findFragmentByTag("MessageFragment");
                        if(tempFragment!=null && tempFragment.isAdded()){
                            transaction.show(tempFragment);
                        }else {
                            tempFragment=new MessageFragment();
                            transaction.add(framelayoutId,tempFragment, "MessageFragment");
                        }
                        setToolBarTitle(textViewTitlename,getResources().getString(R.string.string_home_tab_message));
                        setContactFragmentShowHideRadioButton(false);
                        break;
                }
            transaction.commit();
    }

    private void setContactFragmentShowHideRadioButton(boolean isShow){
            if(isShow){
                radioGroupContact.setVisibility(View.VISIBLE);
                textViewTitlename.setVisibility(View.GONE);
            }else {
                radioGroupContact.setVisibility(View.GONE);
                textViewTitlename.setVisibility(View.VISIBLE);
            }
    }



}
