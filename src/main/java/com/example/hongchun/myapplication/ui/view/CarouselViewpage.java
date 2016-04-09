package com.example.hongchun.myapplication.ui.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hongchun.myapplication.util.ImagerUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

/**
 * Created by TianHongChun on 2016/4/9.
 * 图片轮播初探
 */
public class CarouselViewpage extends ViewPager {

    private int dataSize=0;                 // 图片数
    private OnClickListener onClickListener;
    private OnLongClickListener onLongClickListener;

    private boolean isAutoCarousel=true;
    private final int STOP=-1;
    private final int START=1;
    private final int TIME=3000;

    public CarouselViewpage(Context context) {
        super(context);
        init();
    }

    public CarouselViewpage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.i("======handleMessage===");
            if(msg.what==START){
                if(isAutoCarousel()){
                    int currentItem=getCurrentItem()+1;
                    if(currentItem>getDataSize()-1){
                        currentItem=0;
                    }
                    setCurrentItem(currentItem);
                    sendEmptyMessageDelayed(START,TIME);
                }
            }
        }
    };

    /**
     *
     * @param context
     * @param stringList
     * @param isAutoCarousel
     *
     */
    public void setDatas(Context context,List<String> stringList,boolean isAutoCarousel){
        MyAdapter  adapter=new MyAdapter(context,stringList);
        this.setAdapter(adapter);
        setDataSize(stringList.size());
        setIsAutoCarousel(isAutoCarousel);
        if(isAutoCarousel){
            handler.sendEmptyMessageDelayed(START, TIME);
        }
    }
    public void startCarousel(){
        if(!isAutoCarousel()){
            handler.sendEmptyMessageDelayed(START, TIME);
            setIsAutoCarousel(true);
        }
    }
    public void stopCarousel(){
        if(isAutoCarousel()){
            setIsAutoCarousel(false);
        }
    }

    public void setOnItemClickListener(OnClickListener onClickListener){
        this.onClickListener=onClickListener;

    }
    public void setOnItemLongClickListener(OnLongClickListener onLongClickListener){
        this.onLongClickListener=onLongClickListener;

    }

    private int getDataSize() {
        return dataSize;
    }

    private void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public boolean isAutoCarousel() {
        return isAutoCarousel;
    }

    public void setIsAutoCarousel(boolean isAutoCarousel) {
        this.isAutoCarousel = isAutoCarousel;
    }

    /**
     *
     */
    private class MyAdapter extends PagerAdapter {
        List<String> stringList;
        LayoutInflater inflater;
        Context context;
        ImageView imageView;
        public MyAdapter(Context context,List<String> stringList){
            this.stringList=stringList;
            inflater=LayoutInflater.from(context);
            this.context=context;
        }

        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            imageView=new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            x.image().bind(imageView, stringList.get(position), ImagerUtils.getImageOptions());
            imageView.setId(position);
            container.addView(imageView);
            return imageView;
        }

    }

}
