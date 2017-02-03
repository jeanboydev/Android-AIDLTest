package com.jeanboy.app.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jeanboy.app.aidltest.MyRemoteAidlInterface;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bindRemoteService(View view) {
        Intent intent = new Intent("android.intent.action.REMOTESERVICE");
        intent.setPackage("com.jeanboy.app.aidltest");//5.0以上必须设置aidl所在的包名
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(connection);
        super.onDestroy();
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(TAG, "on client ServiceConnected");

            MyRemoteAidlInterface aidlInterface = MyRemoteAidlInterface.Stub.asInterface(iBinder);
            try {
                int result = aidlInterface.testPlus(2, 5);
                Log.e(TAG, "== client testPlus==result===" + result);
                String upper = aidlInterface.toUpper("hello world!");
                Log.e(TAG, "== client toUpper==upper===" + upper);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(TAG, "on client ServiceDisconnected");
        }
    };
}
