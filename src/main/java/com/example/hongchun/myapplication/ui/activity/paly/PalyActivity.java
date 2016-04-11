package com.example.hongchun.myapplication.ui.activity.paly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.activity.BaseNormActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_paly)
public class PalyActivity extends BaseNormActivity {

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.textview_titlename)
    TextView textViewTitleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    private void initView(){
        initToolBarAndBackButton(toolbar);
        setToolBarTitle(textViewTitleName,"支付");
    }



}
