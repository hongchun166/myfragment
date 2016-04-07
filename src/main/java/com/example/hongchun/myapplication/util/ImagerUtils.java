package com.example.hongchun.myapplication.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.hongchun.myapplication.R;

import org.xutils.image.ImageOptions;

/**
 * Created by HongChun on 2016/4/4.
 */
public class ImagerUtils {

    private static ImageOptions circularImageOptions;
    private static ImageOptions imageOptions;
    private static ImageOptions imageOptionsRadius;
    //圆形图片加载配置
    public static ImageOptions getCircularImageOptions() {
        if (circularImageOptions==null){
            circularImageOptions = new ImageOptions.Builder()
                    .setCircular(true)
                    .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
                    .setUseMemCache(true)
                    .setConfig(Bitmap.Config.RGB_565)
                    .setLoadingDrawableId(R.mipmap.ic_launcher)
                    .setFailureDrawableId(R.mipmap.ic_launcher)
                    .build();
        }
        return circularImageOptions;
    }
    //方形图片加载配置
    public static ImageOptions getImageOptions() {
        if (imageOptions==null){
            imageOptions = new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
                    .setLoadingDrawableId(R.mipmap.ic_launcher)
                    .setFailureDrawableId(R.mipmap.ic_launcher)
                    .setConfig(Bitmap.Config.RGB_565)
                    .build();
        }
        return imageOptions;
    }

    public static ImageOptions getImageOptionsRadius() {
        if (imageOptionsRadius==null){
            imageOptionsRadius = new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
                    .setLoadingDrawableId(R.mipmap.ic_launcher)
                    .setFailureDrawableId(R.mipmap.ic_launcher)
                    .setConfig(Bitmap.Config.RGB_565)
                    .setRadius(30)
                    .build();
        }
        return imageOptionsRadius;
    }
}
