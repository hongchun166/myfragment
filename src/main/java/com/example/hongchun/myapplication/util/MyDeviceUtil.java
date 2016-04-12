package com.example.hongchun.myapplication.util;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


/**
 * Created by Administrator on 2015/8/25.
 */
public class MyDeviceUtil {
    /**
     * 获取当前应用的版本号
     * @param context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static int getVersionCode(Context context) throws PackageManager.NameNotFoundException {
            PackageManager manager=context.getPackageManager();
        PackageInfo info=manager.getPackageInfo(context.getPackageName(),0);
        int code=info.versionCode;
        return code;
    }
    /**
     * 获取当前应用的版本名
     * @param context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        PackageManager manager=context.getPackageManager();
        PackageInfo info=manager.getPackageInfo(context.getPackageName(),0);
        String name=info.versionName;
        return name;
    }

    /**
     *  获取手机设备信息
     * @param context
     * @return
     */
    public static String getDeviceInfo(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        StringBuilder sb = new StringBuilder();
        //sb.append(""+tm.getDeviceId());
        sb.append("123");
//        sb.append("\nDeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion());
//        sb.append("\nLine1Number = " + tm.getLine1Number());
//        sb.append("\nNetworkCountryIso = " + tm.getNetworkCountryIso());
//        sb.append("\nNetworkOperator = " + tm.getNetworkOperator());
//        sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());
//        sb.append("\nNetworkType = " + tm.getNetworkType());
//        sb.append("\nPhoneType = " + tm.getPhoneType());
//        sb.append("\nSimCountryIso = " + tm.getSimCountryIso());
//        sb.append("\nSimOperator = " + tm.getSimOperator());
//        sb.append("\nSimOperatorName = " + tm.getSimOperatorName());
//        sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());
//        sb.append("\nSimState = " + tm.getSimState());
//        sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());
//        sb.append("\nVoiceMailNumber = " + tm.getVoiceMailNumber());
        return sb.toString();
    }

    /**
     * 获取屏幕大小
     * @param context
     * @return
     */
    public static Display getWindownSize(Context context){
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display dm= wm.getDefaultDisplay();
        return dm;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param pxValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param dipValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;

        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    /**
     * 隐藏输入法
     * @param context
     */
    public static void hideSoftInput(Context context){
        if(context==null){
            return;
        }
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
