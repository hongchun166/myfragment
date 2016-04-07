package com.example.hongchun.myapplication.ui.fragment.video;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.fragment.BaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by TianHongChun on 2016/4/5.
 */
@ContentView(R.layout.fragment_video_system_layout)
public class SystemVideoFragment extends BaseFragment {


    Context context;

    @ViewInject(R.id.button_start)
    Button btn_start;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
    @Event(value = R.id.button_start,type = View.OnClickListener.class)
    private void onEvenOnclick(View view){
        if(view.getId()==R.id.button_start){
//            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/Test_Movie.m4v");
//            String path="android.resource://" + context.getPackageName() + "/" + R.raw.aaa;
            String path="http://down13.68mtv.com/MP4/mp413/伤感一哥-亲爱的你要走[68mtv.com].mp4";
            Uri uri = Uri.parse(path);
            //调用系统自带的播放器
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "video/mp4");
            startActivity(intent);
        }
    }
}
