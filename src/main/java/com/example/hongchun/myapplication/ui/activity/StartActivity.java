package com.example.hongchun.myapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.activity.account.LoginActivity;
import com.example.hongchun.myapplication.ui.activity.account.UserExperienceActivity;
import com.example.hongchun.myapplication.util.SPutils;

import org.xutils.view.annotation.ContentView;
/**
 * app启动
 */
/**
 * Created by HongChun on 2016/3/26.
 */
@ContentView(R.layout.activity_start)
public class StartActivity extends BaseNormActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isFirstLogin=SPutils.getInstants(StartActivity.this).getIsFirstLogin();
                if(isFirstLogin){
                    Intent intent=new Intent(StartActivity.this, UserExperienceActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        },1000);

    }
}
