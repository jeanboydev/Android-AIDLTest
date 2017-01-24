package com.jeanboy.app.aidltest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.jeanboy.app.aidltest.service.ForegroundService;
import com.jeanboy.app.aidltest.service.RemoteService;
import com.jeanboy.app.aidltest.service.TestService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        unbindService(connection);
        stopService(new Intent(this, TestService.class));
        stopService(new Intent(this, ForegroundService.class));
        stopService(new Intent(this, RemoteService.class));
        super.onDestroy();
    }

    public void startService(View view) {
        Intent intent = new Intent(this, TestService.class);
        startService(intent);
    }


    public void stopService(View view) {
        Intent intent = new Intent(this, TestService.class);
        stopService(intent);
    }

    public void bindService(View view) {
        Intent intent = new Intent(this, TestService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);//BIND_AUTO_CREATE 绑定自动创建service
    }

    public void unbindService(View view) {
        unbindService(connection);
    }

    public void startForegroundService(View view) {
        Intent intent = new Intent(this, ForegroundService.class);
        startService(intent);
    }

    public void startRemoteService(View view) {
        Intent intent = new Intent(this, RemoteService.class);
        startService(intent);
    }

    public void bindRemoteService(View view) {
        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (iBinder instanceof ForegroundService.MyForegroundBinder) {
                ForegroundService.MyForegroundBinder foregroundBinder = (ForegroundService.MyForegroundBinder) iBinder;
                foregroundBinder.binderTest();
            }

            if (iBinder instanceof MyRemoteAidlInterface) {
                MyRemoteAidlInterface aidlInterface = MyRemoteAidlInterface.Stub.asInterface(iBinder);
                try {
                    int result = aidlInterface.testPlus(2, 5);
                    Log.e(TAG, "==testPlus==result===" + result);
                    String upper = aidlInterface.toUpper("hello world!");
                    Log.e(TAG, "==toUpper==upper===" + upper);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            Log.e(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(TAG, "onServiceDisconnected");
        }
    };

}
