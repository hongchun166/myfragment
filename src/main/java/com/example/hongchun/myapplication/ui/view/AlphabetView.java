package com.example.hongchun.myapplication.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hongchun.myapplication.interfacem.OnTouchAssortListener;
import com.example.hongchun.myapplication.interfacem.implementsm.ImpOnTouchAssortListener;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by TianHongChun on 2016/4/11.
 *  通讯录字母列表view
 *
 */
public class AlphabetView extends View {

    final String[] ALPHABETS= {"★", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
            "X", "Y", "Z", "#" };

    int selectIndex=-1; // 当前选中的字母

    Paint paint=new Paint();
    int textSize=30;        //没选中文字大小
    int textSizeSelect=30;// 点击的文字大小
    int textColor=Color.BLACK;        //没选中文字颜色
    int textColorSelect=Color.RED;// 点击的文字颜色
    int viewBackgroupColor=Color.TRANSPARENT;        //没选中时view背景色
    int viewBackgroupColorSelect=Color.GRAY;// 点击时view背景色

    private OnTouchAssortListener onTouchAssortListener=null;// 选中回掉监听

    public AlphabetView(Context context) {
        super(context);
        initView(context);
    }

    public AlphabetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AlphabetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化
     * @param context
     */
    private void initView(Context context){
        setBackgroundColor(viewBackgroupColor);
    }

    public void setOnTouchAssortListener(ImpOnTouchAssortListener impOnTouchAssortListener) {
        this.onTouchAssortListener = impOnTouchAssortListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int  width= getWidth();
        int height=getHeight();
        int interval=height/(ALPHABETS.length);
        for (int i=0;i<ALPHABETS.length;i++){
                if(i==selectIndex){
                    paint.setColor(textColorSelect);
                    paint.setTextSize(textSizeSelect);
//                    paint.setTypeface(Typeface.DEFAULT_BOLD);// 粗体
                }else {
                    paint.setColor(textColor);
                    paint.setTextSize(textSize);
                }
            paint.setAntiAlias(true);// 抗锯齿
            float x=width/2-paint.measureText(ALPHABETS[i])/2;
            float y=(i+1)*interval;
            canvas.drawText(ALPHABETS[i],x,y,paint);
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //当前Y除每一分的值,就得出Y所在的值位置
        int currentIndex=(int) (event.getY() /(getHeight()/ ALPHABETS.length));

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(viewBackgroupColorSelect);
                if(currentIndex>=0&&currentIndex<ALPHABETS.length){
                    selectIndex=currentIndex;
                }
                if(onTouchAssortListener!=null){
                    onTouchAssortListener.onTouchAssortChanged(ALPHABETS[selectIndex]);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                setBackgroundColor(viewBackgroupColorSelect);
                if(currentIndex!=selectIndex){
                    if(currentIndex>=0&&currentIndex<ALPHABETS.length){
                        selectIndex=currentIndex;
                    }
                    if(onTouchAssortListener!=null){
                        onTouchAssortListener.onTouchAssortChanged(ALPHABETS[selectIndex]);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(viewBackgroupColor);
                selectIndex=-1;
                if(onTouchAssortListener!=null){
                    onTouchAssortListener.onTouchAssortUP();
                }
                break;
        }
        invalidate();
        return true;
    }

}
