package com.example.hongchun.myapplication.util;

import android.content.Context;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * 证书信任管理器（用于https请求） 
 *  
 */
public class MyX509TrustManager implements X509TrustManager {

    private final static String BKS_FILE_NAME = "dwt.wenshitech.com.bks";

    private final static String KEYSTORE_PASS = "Wenshi85563116";

    X509TrustManager sunJSSEX509TrustManager;

    public MyX509TrustManager(Context context) throws Exception{

        InputStream in = null;
        in = context.getResources().getAssets().open(BKS_FILE_NAME);
        KeyStore ks = KeyStore.getInstance("BKS");
        ks.load(in,
                KEYSTORE_PASS.toCharArray());

        //关闭文件流
        in.close();

        TrustManagerFactory tmf =
                TrustManagerFactory.getInstance("X509");

        tmf.init(ks);

        TrustManager tms [] = tmf.getTrustManagers();

        for (int i = 0; i < tms.length; i++) {
            if (tms[i] instanceof X509TrustManager) {
                sunJSSEX509TrustManager = (X509TrustManager) tms[i];
                return;
            }
        }
        /*
         * Find some other way to initialize, or else we have to fail the
         * constructor.
         */
        throw new Exception("Couldn't initialize");
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try {
            sunJSSEX509TrustManager.checkClientTrusted(chain, authType);
        } catch (CertificateException excep) {
            // do any special handling here, or rethrow exception.
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try {
            sunJSSEX509TrustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException excep) {
                /*
                 * Possibly pop up a dialog box asking whether to trust the
                 * cert chain.
                 */
        }
    }

    public X509Certificate[] getAcceptedIssuers() {
        return sunJSSEX509TrustManager.getAcceptedIssuers();
    }  
}