package com.example.hongchun.myapplication.ui.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.activity.BaseNormActivity;
import com.example.hongchun.myapplication.ui.adapter.ExperienceVPAdapter;
import com.example.hongchun.myapplication.util.SPutils;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
/**
 * 用户体验
 */
/**
 * Created by HongChun on 2016/4/4.
 */
@ContentView(R.layout.activity_experience)
public class UserExperienceActivity extends BaseNormActivity {

    @ViewInject(R.id.viewpage)
    ViewPager viewPager;

    @ViewInject(R.id.button_now_experience)
    Button btn_experience;

    @ViewInject(R.id.exerience_RadioGroup)
    RadioGroup radioGroup;

    RadioButton tempRadioButton;

    List<String> datas;
    ExperienceVPAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEven();
        initView();
    }

    private void initView(){
        datas=new ArrayList<>();
        datas.add("assets://experience/launch_1.png");
        datas.add("assets://experience/launch_2.png");
        datas.add("assets://experience/launch_3.png");
        datas.add("assets://experience/launch_4.png");
        mAdapter=new ExperienceVPAdapter(this,datas);
        viewPager.setAdapter(mAdapter);

        {
            LayoutInflater inflater=LayoutInflater.from(this);
            if(datas.size()>1){
                for (int i=0;i<datas.size();i++) {
                    View viewRadioBtn = inflater.inflate(R.layout.look_photo_radiobtn, null);
                    RadioButton radioBtn = (RadioButton) viewRadioBtn.findViewById(R.id.radioButton_point);
                    radioBtn.setEnabled(false);
                    if (i == 0) {
                        radioBtn.setChecked(true);
                        tempRadioButton=radioBtn;
                    }
                    radioGroup.addView(viewRadioBtn);
                }
            }
        }

    }

    @Event(value = {R.id.button_now_experience},type = View.OnClickListener.class)
    private void onEvenOncliclk(View view){
        if(view.getId()==R.id.button_now_experience){
            SPutils.getInstants(this).putFirstLoginNo();
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
    }

    private void initEven(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {

                tempRadioButton.setChecked(false);
                RadioButton radiobtn= ((RadioButton)radioGroup.getChildAt(position).findViewById(R.id.radioButton_point));
                radiobtn.setChecked(true);
                tempRadioButton=radiobtn;

                if(position==datas.size()-1){
                    btn_experience.setVisibility(View.VISIBLE);
                }else {
                    btn_experience.setVisibility(View.GONE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
