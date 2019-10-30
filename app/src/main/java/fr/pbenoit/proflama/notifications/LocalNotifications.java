package fr.pbenoit.proflama.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import fr.pbenoit.proflama.ProfLama;
import fr.pbenoit.proflama.R;

public class LocalNotifications {

    private static final String CHANNEL_ID = "channel_prof_lama";

    public static void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, ProfLama.getAppContext().getString(R.string.app_name), importance);
            channel.setDescription("Prof Lama notifications");
            NotificationManager notificationManager = ProfLama.getAppContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void sendWorkCreationNotification(PendingIntent pendingIntent, String title) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ProfLama.getAppContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(ProfLama.getAppContext().getString(R.string.app_name))
                .setContentText(ProfLama.getAppContext().getString(R.string.pushNotificationNewTitleContent)+ title)
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        android.app.NotificationManager notificationManager =
                (android.app.NotificationManager) ProfLama.getAppContext().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    public static void sendDailyReminderNotification(PendingIntent pendingIntent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ProfLama.getAppContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(ProfLama.getAppContext().getString(R.string.app_name))
                .setContentText(ProfLama.getAppContext().getString(R.string.dailyNotificationContent))
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        android.app.NotificationManager notificationManager =
                (android.app.NotificationManager) ProfLama.getAppContext().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    public static void sendWeekSummaryNotification(PendingIntent pendingIntent, int numberOfWord) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ProfLama.getAppContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(ProfLama.getAppContext().getString(R.string.app_name))
                .setContentText(ProfLama.getAppContext().getString(R.string.weeklyNotificationContent, numberOfWord))
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        android.app.NotificationManager notificationManager =
                (android.app.NotificationManager) ProfLama.getAppContext().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(2, builder.build());
    }

}
