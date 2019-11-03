package fr.pbenoit.proflama.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import fr.pbenoit.proflama.ProfLama;
import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.services.NotesUtils;

public class LocalNotifications {

    private static final String CHANNEL_ID = "channel_prof_lama";

    public static void createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        NotificationManager notificationManager = ProfLama.getAppContext().getSystemService(NotificationManager.class);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, ProfLama.getAppContext().getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Prof Lama notifications");
        notificationManager.createNotificationChannel(channel);
    }

    public static void sendWorkCreationNotification(PendingIntent pendingIntent, String title) {
        LocalNotifications.createNotificationChannel();

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
        LocalNotifications.createNotificationChannel();
        String content;

        int numberOfWordAddedToday = NotesUtils.countNumberOfWordAddToday();
        if (numberOfWordAddedToday > 0 ) {
            content = ProfLama.getAppContext().getString(R.string.dailyNotificationWhenWordWasAddedContent, numberOfWordAddedToday);
        } else {
            content = ProfLama.getAppContext().getString(R.string.dailyNotificationContent);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ProfLama.getAppContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(ProfLama.getAppContext().getString(R.string.app_name))
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        android.app.NotificationManager notificationManager =
                (android.app.NotificationManager) ProfLama.getAppContext().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    public static void sendWeekSummaryNotification(PendingIntent pendingIntent, int numberOfWord) {
        LocalNotifications.createNotificationChannel();

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
