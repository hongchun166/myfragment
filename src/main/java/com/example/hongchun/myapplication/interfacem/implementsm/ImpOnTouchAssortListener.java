package com.example.hongchun.myapplication.interfacem.implementsm;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.interfacem.OnTouchAssortListener;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by TianHongChun on 2016/4/11.
 */
public  class ImpOnTouchAssortListener implements OnTouchAssortListener {



    Context context;
    PopupWindow popupWindow;

    TextView textViewContent;
    boolean isShowCallbackPopupWindow=true;
    RelativeLayout relativeLayout;

    public ImpOnTouchAssortListener(Context context){
            this.context=context;
    }
    public ImpOnTouchAssortListener(Context context,boolean isShowCallbackPopupWindow){
        this.context=context;
        this.isShowCallbackPopupWindow=isShowCallbackPopupWindow;
    }

    @Override
    public void onTouchAssortChanged(String s) {
        if(isShowCallbackPopupWindow){
            if(popupWindow==null){
                relativeLayout=new RelativeLayout(context);
                relativeLayout.setBackgroundColor(Color.parseColor("#70000000"));
                relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textViewContent=new TextView(context);
                textViewContent.setId(0x00000000);
                textViewContent.setTextColor(Color.parseColor("#ffffff"));
                textViewContent.setTextSize(26);
                textViewContent.setGravity(Gravity.CENTER);
                relativeLayout.addView(textViewContent, new ViewGroup.LayoutParams(150, 150));
                x.view().inject(this, relativeLayout);

                popupWindow=new PopupWindow(relativeLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                        false);
            }
            textViewContent.setText(s);
            popupWindow.showAtLocation(((Activity)context).getWindow().getDecorView(), Gravity.CENTER,0,0);
        }
    }
    @Override
    public void onTouchAssortUP() {
        if(popupWindow!=null){
            popupWindow.dismiss();
            popupWindow=null;
            relativeLayout=null;
            textViewContent=null;

        }
    }

}
