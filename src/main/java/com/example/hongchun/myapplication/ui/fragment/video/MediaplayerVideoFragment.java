package com.example.hongchun.myapplication.ui.fragment.video;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.activity.MainActivity;
import com.example.hongchun.myapplication.ui.fragment.BaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.IOException;

/**
 * Created by TianHongChun on 2016/4/5.
 */
@ContentView(R.layout.fragment_video_mediaplayer_layout)
public class MediaplayerVideoFragment extends BaseFragment {


    Context context;

    @ViewInject(R.id.surfaceView1)
    SurfaceView surfaceView;

    @ViewInject(R.id.button_pause)
    Button btn_pause;
    @ViewInject(R.id.button_play)
    Button btn_play;
    @ViewInject(R.id.button_stop)
    Button btn_stop;

    MediaPlayer mp;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mp=new MediaPlayer();
        initEven();
    }

    private void initEven(){
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(context, "视频播放完毕！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Event(value ={R.id.button_play,R.id.button_pause,R.id.button_stop},type = View.OnClickListener.class)
    private void onEvenOnclick(View view){
        switch (view.getId()){
            case R.id.button_play:
                mp.reset();
                try {
                    String path="http://down13.68mtv.com/MP4/mp413/伤感一哥-亲爱的你要走[68mtv.com].mp4";
                    mp.setDataSource(path);
                    mp.setDisplay(surfaceView.getHolder());
                    mp.prepare();
                    mp.start();
                    btn_pause.setText("暂停");
                    btn_pause.setEnabled(true);
                }catch(IllegalArgumentException e) {
                    e.printStackTrace();
                }catch(SecurityException e) {
                    e.printStackTrace();
                }catch(IllegalStateException e) {
                    e.printStackTrace();
                }catch(IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_puse:
                if(mp.isPlaying()){
                    mp.pause();
                    ((Button)view).setText("继续");
                }else{
                    mp.start();
                    ((Button)view).setText("暂停");
                }

                break;
            case R.id.button_stop:
                if(mp.isPlaying()){
                    mp.stop();
                    btn_pause.setEnabled(false);
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        if(mp.isPlaying()){
            mp.stop();
        }
        mp.release();
        super.onDestroyView();
    }
}
