package com.example.hongchun.myapplication.util;

import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import org.xutils.common.util.LogUtil;

/**
 * Created by TianHongChun on 2016/4/26.
 * chat helper utils
 */
public class ChatHelper {

    private static ChatHelper chatHelper;



    private ChatHelper(){}
    public static ChatHelper getInstance(){
        if (chatHelper == null) {
            chatHelper = new ChatHelper();
        }
        return chatHelper;
    }

    public void init(Context context){

        EMOptions options = initChatOptions();
    //初始化
        EMClient.getInstance().init(context, options);
    //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    private EMOptions initChatOptions(){
        LogUtil.d("init HuanXin Options");
        // 获取到EMChatOptions对象
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 设置是否需要已读回执
        options.setRequireAck(true);
        // 设置是否需要已送达回执
        options.setRequireDeliveryAck(false);

        return options;
    }
}
