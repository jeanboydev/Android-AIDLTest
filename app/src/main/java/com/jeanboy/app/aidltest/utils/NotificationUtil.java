package com.jeanboy.app.aidltest.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.jeanboy.app.aidltest.MainActivity;
import com.jeanboy.app.aidltest.R;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by jeanboy on 2017/1/24.
 */

public class NotificationUtil {

    public static void showNotification(Context context) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("通知栏标题");
        builder.setContentText("这是一个普通的通知栏");
        builder.setWhen(System.currentTimeMillis());

        // 设置通知的提示音
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        builder.setSound(alarmSound);

        /**
         * 设置通知的点击行为：这里启动一个 Activity
         *  PendingIntent的位标识符：
         FLAG_ONE_SHOT   表示返回的PendingIntent仅能执行一次，执行完后自动取消
         FLAG_NO_CREATE     表示如果描述的PendingIntent不存在，并不创建相应的PendingIntent，而是返回NULL
         FLAG_CANCEL_CURRENT      表示相应的PendingIntent已经存在，则取消前者，然后创建新的PendingIntent，这个有利于数据保持为最新的，可以用于即时通信的通信场景
         FLAG_UPDATE_CURRENT     表示更新的PendingIntent
         */
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // 发送通知 id 需要在应用内唯一
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(id, builder.build());


        //扩展样式通知
//        NotificationCompat.Builder builderCompat = new NotificationCompat.Builder(getApplicationContext());
//        builderCompat.setSmallIcon(R.mipmap.ic_launcher);
//        builderCompat.setContentTitle("My notification");
//        builderCompat.setContentText("Hello World!");
//        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//        inboxStyle.setBigContentTitle("邮件标题：");
//        for (int i=0; i < 5; i++) {
//            inboxStyle.addLine("邮件内容" + i);
//        }
//        builderCompat.setStyle(inboxStyle);


        //Android 5.0（API 21）引入浮动通知的展现形式，需要设置 Notification 的优先级为 PRIORITY_HIGH 或者 PRIORITY_MAX 并且使用铃声或振动
        builder.setPriority(Notification.PRIORITY_MAX);//优先级
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setVisibility(Notification.VISIBILITY_PUBLIC);//Android 5.0（API 21）锁屏显示
        }


        RemoteViews smallViews = new RemoteViews(context.getPackageName(), R.layout.notification_test_small);// 获取remoteViews（参数一：包名；参数二：布局资源）
        RemoteViews bigViews = new RemoteViews(context.getPackageName(), R.layout.notification_test_big);// 获取remoteViews（参数一：包名；参数二：布局资源）

        //设置自定义Notification内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setCustomContentView(smallViews);
        } else {
            builder.setContent(smallViews);
        }

        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_SOUND;//默认声音
//        Notification.DEFAULT_VIBRATE    //添加默认震动提醒  需要 VIBRATE permission
//        Notification.DEFAULT_SOUND    // 添加默认声音提醒
//        Notification.DEFAULT_LIGHTS// 添加默认三色灯提醒
//        Notification.DEFAULT_ALL// 添加默认以上3种全部提醒
        notification.flags = Notification.FLAG_AUTO_CANCEL;
//        提醒标志符成员：
//        Notification.FLAG_SHOW_LIGHTS              //三色灯提醒，在使用三色灯提醒时候必须加该标志符
//        Notification.FLAG_ONGOING_EVENT          //发起正在运行事件（活动中）
//        Notification.FLAG_INSISTENT   //让声音、振动无限循环，直到用户响应 （取消或者打开）
//        Notification.FLAG_ONLY_ALERT_ONCE  //发起Notification后，铃声和震动均只执行一次
//        Notification.FLAG_AUTO_CANCEL      //用户单击通知后自动消失
//        Notification.FLAG_NO_CLEAR          //只有全部清除时，Notification才会清除 ，不清楚该通知(QQ的通知无法清除，就是用的这个)
//        Notification.FLAG_FOREGROUND_SERVICE    //表示正在运行的服务

        notification.bigContentView = bigViews;
    }

    public static Notification getNotification(Context context) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("通知栏标题");
        builder.setContentText("这是一个普通的通知栏");
        builder.setWhen(System.currentTimeMillis());

        builder.setPriority(Notification.PRIORITY_MAX);//悬浮通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setVisibility(Notification.VISIBILITY_PUBLIC);//Android 5.0（API 21）锁屏显示
        }

        RemoteViews smallViews = new RemoteViews(context.getPackageName(), R.layout.notification_test_small);
        RemoteViews bigViews = new RemoteViews(context.getPackageName(), R.layout.notification_test_big);

        //设置自定义Notification内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setCustomContentView(smallViews);
        } else {
            builder.setContent(smallViews);
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_ALL;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.bigContentView = bigViews;
        return notification;
    }

}
