package com.jeanboy.app.aidltest.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jeanboy.app.aidltest.utils.NotificationUtil;

/**
 * Created by jeanboy on 2017/1/24.
 * <p>
 * <p>
 * 前台服务必须得有通知栏提醒才可以开启
 */

public class ForegroundService extends Service {

    private static final String TAG = ForegroundService.class.getName();


    private Notification notification;

    private MyForegroundBinder foregroundBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        notification = NotificationUtil.getNotification(getApplicationContext());
        foregroundBinder = new MyForegroundBinder();
        Log.e(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(110, notification);//开始前台服务
        Log.e(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        return foregroundBinder;
    }

    public class MyForegroundBinder extends Binder {

        public void binderTest() {
            Log.e(TAG, "binderTest");
        }
    }
}
