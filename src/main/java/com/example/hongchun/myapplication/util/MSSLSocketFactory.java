package com.example.hongchun.myapplication.util;

import org.xutils.x;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/**SSL证书工厂
 * Created by TangJiaHong on 2016/1/25.
 */
public class MSSLSocketFactory {

    public static javax.net.ssl.SSLSocketFactory getSSLSocketFactory(){
        SSLContext sslContext = null;
        try {

            TrustManager[] tm = { new MyX509TrustManager(x.app().getApplicationContext()) };

            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tm, new java.security.SecureRandom());

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            javax.net.ssl.SSLSocketFactory ssf = sslContext.getSocketFactory();

            return ssf;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
