package com.wennuan.olddriver.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.baidu.mapapi.SDKInitializer;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/13
 * Time:14:40
 * E-mail:962570483@qq.com
 */

public class BaseApplication extends Application {

    private static Context sContext;

    public static Context getsContext() {
        return sContext;
    }

    public static String getCachePath() {
        return sContext.getExternalCacheDir().getAbsolutePath();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        SDKInitializer.initialize(getApplicationContext());

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"dNB25FYKM7CU3LrKEe3xSkbF-gzGzoHsz","GHpjqssOs2d7ygL2fHesspdw");

        AVIMMessageManager.registerDefaultMessageHandler(new CustomMessageHandler());

        AVIMClient.setMessageQueryCacheEnable(true);

    }

    public static class CustomMessageHandler extends AVIMMessageHandler
    {
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
            if(message instanceof AVIMTextMessage){
                Log.d("default handler:",((AVIMTextMessage)message).getText());
            }
        }

        @Override
        public void onMessageReceipt(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
            super.onMessageReceipt(message, conversation, client);
        }
    }
}
