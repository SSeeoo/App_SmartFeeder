package com.example.smartfeeder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {

    private static final String CHANNEL_ID = "MOTOR_NOTIFICATION_CHANNEL";

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Motor Notification";
            String description = "Notifications about motor activation";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    // If you don't plan on using this method soon, you can comment it out to resolve the warning.
    // public static void showNotification(Context context, String title, String message) {
    //     NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
    //             .setSmallIcon(R.drawable.icon)
    //             .setContentTitle(title)
    //             .setContentText(message)
    //             .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    //
    //     NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
    //     notificationManager.notify(0, builder.build());
    // }
}
