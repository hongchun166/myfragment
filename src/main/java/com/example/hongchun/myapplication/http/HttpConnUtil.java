package com.example.hongchun.myapplication.http;
import android.content.Context;
import android.net.Uri;

import com.example.hongchun.myapplication.util.MSSLSocketFactory;
import com.example.hongchun.myapplication.util.SPutils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**Http请求方法
 * Created by THC on 2016/2/20.
 */
public class HttpConnUtil {

    public static void connHttpPostByXUtil(RequestParams requestParams,Callback.CommonCallback<String> listen ){
        requestParams.setSslSocketFactory(MSSLSocketFactory.getSSLSocketFactory());
        x.http().post(requestParams, listen);
    }

    /**
     * 获取初始化提交参数
     * @param context
     * @return
     */
    public static RequestParams getRequestParams(Context context,String uri){
        RequestParams requestParams = new RequestParams(uri);
        requestParams.addQueryStringParameter("userName", SPutils.getInstants(context).getUserName());
        requestParams.addQueryStringParameter("userPwd", SPutils.getInstants(context).getPassWord());
        return requestParams;
    }
}
