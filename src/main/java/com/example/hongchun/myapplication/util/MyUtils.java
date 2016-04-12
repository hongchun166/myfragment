package com.example.hongchun.myapplication.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.Time;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by TianHongChun on 2016/4/5.
 */
public class MyUtils {

    /**
     * MD5加密
     * @param str
     * @return
     */
    public static String encodeByMd5(String str) {
        //确定计算方法
        //目前广泛使用的算法有MD4、MD5、SHA-1，jdk1.5对上面都提供了支持，在java中进行消
        //息摘要很简单， java.security.MessageDigest提供了一个简易的操作方法：
        //使用getInstance("算法")来获得消息摘要,这里使用SHA-1的160位算法
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //加密后的字符串
        String newStr = null;
        try {
            newStr = Base64.encodeToString(md5.digest(str.getBytes("utf8")), Base64.DEFAULT);
            System.out.println(newStr);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newStr;
    }

    /**
     * 检查是否是手机格式
     * @param phone
     * @return
     */
    public static boolean isIPhone(String phone) {
        String phoneRegex = "[1][3587]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        return phone.matches(phoneRegex);
    }

    /**
     * 验证号码 手机号 固话均可
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
//        String expression="(^(\\d{3}-?\\d{8})|(\\d{4}-?\\d{7})$|(^(400)[0-9]{7}$))";
        String expression = "((^(13|15|18|17)[0-9]{9}$)"
                +"|((0\\d{2}-?\\d{8})|(0\\d{3}-?\\d{7})$)"
                +"|((0\\d{2}-?\\d{8})|(0\\d{3}-?\\d{8})$)"
                +"|((0\\d{2}-?\\d{7})|(0\\d{3}-?\\d{8})$)"
                +"|(^(400)[0-9]{7}$))";
        CharSequence inputStr = phoneNumber;

        Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;

    }

    /**
     * 判断email格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {

        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
//        String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(str);

        Matcher m = p.matcher(email);

        return m.matches();

    }

    /**
     * 获取当前时间Time
     * @return
     */
    public static Time getDateTime(){
        Time time=new Time("GMT+8");
        time.setToNow();
        return time;
    }
    /**
     * 获取当前时间String 默认 yyyy-MM-dd HH:mm:ss
     * @param pattern  定义时间格式
     * @return
     */
    public static String getDateStr(String pattern) {
        if (pattern == null || "".equals(pattern.trim())) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateValue = simpleDateFormat.format(new Date());
        return dateValue;
    }
    /**
     * 将时间字符串装换成 时间格式 默认 yyyy-MM-dd HH:mm:ss
     *
     * @param dateString
     * @return
     */
    public static Date stringToDate(String dateString, String pattern) {
        if (pattern == null || pattern.trim().equals("")) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        Date dateValue = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            dateValue = simpleDateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateValue;
    }
    /**
     * 判断当前网络是否是wifi局域网
     *
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (info != null) {
            return info.isConnected(); // 返回网络连接状态
        }
        return false;
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName
     *   是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }


}
