package com.example.cmma;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NotificationUtils extends ContextWrapper {

    private NotificationManager mManager;
    public static final String CHANNEL_ID = "com.example.cmma.QuantityMaterial";
    public static final String CHANNEL_NAME = "Material Quantity reminder";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationUtils(Context base) {
        super(base);
        createChannels();
        String groupId = "group_id_101";
        CharSequence groupName = "Channel Name";
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannelGroup(new NotificationChannelGroup(groupId, groupName));
    }
    public void createChannels() {

        // create android channel
        NotificationChannel androidChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            androidChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            androidChannel.enableLights(true);
            androidChannel.enableVibration(true);
            androidChannel.setLightColor(Color.GREEN);
            androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(androidChannel);

        }

    }

    NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getAndroidChannelNotification(String title, String body) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(android.R.drawable.stat_notify_more)
                    .setAutoCancel(true);
        } else {
            return new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(android.R.drawable.stat_notify_more)
                    .setAutoCancel(true);

        }
    }


}