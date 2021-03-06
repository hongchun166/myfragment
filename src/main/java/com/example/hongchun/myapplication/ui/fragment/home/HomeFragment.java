package com.example.hongchun.myapplication.ui.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.activity.map.MapActivity;
import com.example.hongchun.myapplication.ui.activity.paly.PalyActivity;
import com.example.hongchun.myapplication.ui.activity.video.VideoActivity;
import com.example.hongchun.myapplication.ui.fragment.BaseFragment;
import com.example.hongchun.myapplication.ui.view.CarouselViewpage;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by TianHongChun on 2016/4/5.
 */
@ContentView(R.layout.fragment_home_layout)
public class HomeFragment extends BaseFragment {

    Context context;

    @ViewInject(R.id.viewpage_carouselViewpage)
    CarouselViewpage carouselViewpage;
    @ViewInject(R.id.textview_marquee_forever)
    TextView marqueeForever;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> stringList=new ArrayList<>();
        stringList.add("assets://experience/launch_1.png");
        stringList.add("assets://experience/launch_2.png");
        stringList.add("assets://experience/launch_3.png");
        stringList.add("assets://experience/launch_4.png");
        carouselViewpage.setDatas(context, stringList,true);
        marqueeForever.setSelected(true);
    }

    @Override
    public void initEven(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {

    }

    @Event(value = {R.id.textview_video,R.id.textview_game,R.id.textview_map
            ,R.id.textview_pay,R.id.textview_read,R.id.textview_setting}
            ,type = View.OnClickListener.class)
    private void onEvenOnclick(View view){
        Intent intent=null;
            switch (view.getId()){
                case R.id.textview_video:
                    intent=new Intent(context,VideoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.textview_game:
                    break;
                case R.id.textview_map:
                    intent=new Intent(context,MapActivity.class);
                    startActivity(intent);
                    break;
                case R.id.textview_pay:
                    intent=new Intent(context, PalyActivity.class);
                    startActivity(intent);
                    break;
                case R.id.textview_read:
                    break;
                case R.id.textview_setting:
                    break;
            }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            carouselViewpage.stopCarousel();
        }else {
            carouselViewpage.startCarousel();
        }
    }

}
