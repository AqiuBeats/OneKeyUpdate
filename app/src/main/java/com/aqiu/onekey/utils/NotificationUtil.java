package com.aqiu.onekey.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.aqiu.onekey.R;

/**
 * 通知管理工具类
 * Created by aqiu on 2017/3/17.
 */

public class NotificationUtil {
    private int indexProgress;
    private long timeIndex;
    private Context mContext;
    // NotificationManager ： 是状态栏通知的管理类，负责发通知、清楚通知等。
    private NotificationManager manager;
    // 定义Map来保存Notification对象
    //    private Map<Integer,  NotificationCompat.Builder> map = null;
    private NotificationCompat.Builder sBuilder;

    public NotificationUtil(Context context) {
        this.mContext = context;
        timeIndex=System.currentTimeMillis();
        indexProgress = 0;
        // NotificationManager 是一个系统Service，必须通过 getSystemService()方法来获取。
        manager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        //        map = new HashMap<Integer,  NotificationCompat.Builder>();
    }

    public NotificationManager getManager() {
        return manager;
    }

    public void showNotification(int notificationId) {
        RemoteViews remoteViews = new RemoteViews(
                mContext.getPackageName(), R.layout.notification);
        sBuilder = new NotificationCompat.Builder(mContext);
        // 设置默认的声音和震动
        sBuilder
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.push)
                .setContentTitle("版本更新 :");
        sBuilder.setProgress(0, 0, true);//设置为true，表示流动
        manager.notify(notificationId, sBuilder.build());
    }

    /**
     * 取消通知操作
     */
    public void cancel(int notificationId) {
        manager.cancel(notificationId);
        //        map.remove(notificationId);
    }

    public void updateProgress(int notificationId, int progress) {
        // 修改进度条
        sBuilder.setProgress(100, progress, false)
                .setContentText("下载" + progress + "%");
        int num = progress - indexProgress;
        long numIndex=System.currentTimeMillis()-timeIndex;
        if (num >= 3 || indexProgress == 0) {//尽量减少通知栏的刷新次数,并且屏掉声音和震动(不然会鬼畜)
            manager.notify(notificationId, sBuilder.build());
            indexProgress = progress;
        }
//        if (numIndex >= 2000) {//2秒刷新一次
//            manager.notify(notificationId, sBuilder.build());
//            indexProgress = progress;
//        }
    }

    public void notifyBuilder(int notificationId) {
        manager.notify(notificationId, sBuilder.build());
    }
}
