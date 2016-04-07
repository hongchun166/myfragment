package com.example.hongchun.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HongChun on 2016/4/4.
 */
public class SPutils {

    private  static SPutils sPutils;
    private static SharedPreferences sharedPreferences;

    private SPutils(){

    }
    public static synchronized SPutils getInstants(Context context){
        if(sPutils==null){
            sPutils=new SPutils();
        }
        if(sharedPreferences==null){
            sharedPreferences=context.getSharedPreferences(MConstants.SPutilsFinal.SP_NAME,Activity.MODE_PRIVATE);
        }
        return sPutils;
    }

    public void putUserNamePsw(String userName,String password){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(MConstants.SPutilsFinal.SP_USER_NAME, userName);
        editor.putString(MConstants.SPutilsFinal.SP_USER_PSW, password).commit();
    }
    public String getUserName(){
        return sharedPreferences.getString(MConstants.SPutilsFinal.SP_USER_NAME,"");
    }
    public String getPassWord(){
        return sharedPreferences.getString(MConstants.SPutilsFinal.SP_USER_PSW,"");
    }


    public void putFirstLoginNo(){
        sharedPreferences.edit().putBoolean(MConstants.SPutilsFinal.SP_IS_FIRST_LOGIN, false).commit();
    }
    public boolean getIsFirstLogin(){
       return sharedPreferences.getBoolean(MConstants.SPutilsFinal.SP_IS_FIRST_LOGIN,true);
    }

}
