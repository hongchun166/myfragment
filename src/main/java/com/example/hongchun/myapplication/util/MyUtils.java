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

    static Random random=new Random();
    private static char[] chars = new char[] { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z' };
    /**
     * 取随机的字符串
     * @return
     */
    public static String getRandomString(int length) {
        char[] r = new char[length];
        for (int i = 0; i < r.length; i++) {
            r[i] = chars[random.nextInt(chars.length)];
        }
        return String.valueOf(r);
    }

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
     * 判断是否全部为中文字符
     * @param s
     * @return
     */
    public static boolean checkfilename(String s){
        s=new String(s.getBytes());//用GBK编码
        String pattern="[\u4e00-\u9fa5]+";
        Pattern p= Pattern.compile(pattern);
        Matcher result=p.matcher(s);
        return result.matches(); //是否含有中文字符
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


    /**
     * 设置字体颜色
     */
    public static SpannableStringBuilder textAddColor(String content,int start,int end,int color){
        /// 改变字体hint颜色， 也可改变大小....
        if(content==null||content.equals("")){
            content="";
        }
        if(start<0){
            start=0;
        }
        if(end<0){
            end=content.length();
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(content);
        ForegroundColorSpan fcs=new ForegroundColorSpan(color);
        ssb.setSpan(fcs, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint<br />
        return ssb;
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


    /**
     * 将字符串中的中文转化为拼音,其他字符不变
     *
     * @param inputString
     * @return
     */
    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = inputString.trim().toCharArray();
        String output = "";

        try {
            for (int i = 0; i < input.length; i++) {
                if (java.lang.Character.toString(input[i]).
                        matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.
                            toHanyuPinyinStringArray(input[i],
                                    format);
                    output += temp[0];
                } else
                    output += java.lang.Character.toString(
                            input[i]);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * 汉字转换位汉语拼音首字母，英文字符不变
     * @param chines 汉字
     * @return 拼音
     */
    public static String converterToFirstSpell(String chines){
        String pinyinName = "";

        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }else{
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }
}
