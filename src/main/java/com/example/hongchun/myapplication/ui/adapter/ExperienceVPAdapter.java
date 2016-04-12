package com.example.hongchun.myapplication.ui.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.util.ImagerUtils;

import org.xutils.x;

import java.util.List;

/**
 * Created by HongChun on 2016/4/4.
 * 欢迎页面viewpage适配器
 */
public class ExperienceVPAdapter extends PagerAdapter  {

    Context context;
    LayoutInflater inflater;
    List<String> datas;
    public ExperienceVPAdapter(Context context,List<String> datas){
        this.datas=datas;
        this.context=context;
        this.inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return datas.size();
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
        View view=inflater.inflate(R.layout.experience_item_layout, null);
        ImageView imageView=(ImageView)view.findViewById(R.id.imageView);
        x.image().bind(imageView,datas.get(position), ImagerUtils.getImageOptions());
        container.addView(view);
        view.setId(position);
        return view;
    }
}
