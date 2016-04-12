package com.example.hongchun.myapplication.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.application.MyApplication;

public class BaseExitActivity extends BaseActivity {

    long tempLongTimeMillis=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //双击后退按钮关闭应用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-tempLongTimeMillis) > 2000){
                Toast.makeText(getApplicationContext(), R.string.tost_exit_app_by_click_double, Toast.LENGTH_SHORT).show();
                tempLongTimeMillis = System.currentTimeMillis();
            } else {
                MyApplication.getApplication().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
