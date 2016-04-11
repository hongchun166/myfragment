package com.example.hongchun.myapplication.ui.fragment.video;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import android.media.MediaPlayer.OnCompletionListener;
import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.fragment.BaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * Created by TianHongChun on 2016/4/5.
 */
@ContentView(R.layout.fragment_video_videoview_layout)
public class VideoViewVideoFragment extends BaseFragment {


    Context context;

    @ViewInject(R.id.videoview)
    VideoView videoView;

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

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {

    }
    @Override
    public void initEven(View view, @Nullable Bundle savedInstanceState) {
        String uri="http://down13.68mtv.com/MP4/mp413/伤感一哥-亲爱的你要走[68mtv.com].mp4";
        videoView.setMediaController(new MediaController(context));
        videoView.setVideoURI(Uri.parse(uri));
    }

    @Event(value = {R.id.button_start,R.id.button_puse,R.id.button_stop},type = View.OnClickListener.class)
    private void onEvenOnclick(View view){
        switch (view.getId()){
            case R.id.button_stop:
                videoView.stopPlayback();
                videoView.requestFocus();
                break;
            case R.id.button_start:
                videoView.start();
                videoView.requestFocus();
                break;
            case R.id.button_puse:
                videoView.pause();
                videoView.requestFocus();
                break;
        }
    }

}
