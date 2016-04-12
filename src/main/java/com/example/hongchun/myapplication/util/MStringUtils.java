package com.example.hongchun.myapplication.util;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by TianHongChun on 2016/4/12.
 */
public class MStringUtils {


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
