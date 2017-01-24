package com.jeanboy.app.aidltest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jeanboy.app.aidltest.MyRemoteAidlInterface;

/**
 * Created by jeanboy on 2017/1/24.
 */

public class RemoteService extends Service {

    private static final String TAG = RemoteService.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        return aidlInterface.asBinder();
    }

    MyRemoteAidlInterface aidlInterface = new MyRemoteAidlInterface.Stub() {

        @Override
        public int testPlus(int a, int b) throws RemoteException {
            return a + b;
        }

        @Override
        public String toUpper(String str) throws RemoteException {
            if (str != null) {
                return str.toUpperCase();
            }
            return null;
        }
    };
}
