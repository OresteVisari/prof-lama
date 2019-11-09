package fr.pbenoit.proflama.notifications;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

import fr.pbenoit.proflama.ProfLama;
import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.repositories.JsonFileRepository;
import fr.pbenoit.proflama.utilities.Logger;
import fr.pbenoit.proflama.utilities.NotesUtils;

public class NotificationManager {

    private static final String CHANNEL_ID = "channel_prof_lama";

    public static void createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        android.app.NotificationManager notificationManager = ProfLama.getAppContext().getSystemService(android.app.NotificationManager.class);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, ProfLama.getAppContext().getString(R.string.app_name), android.app.NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Prof Lama notifications");
        notificationManager.createNotificationChannel(channel);
    }

    public static void sendWorkCreationNotification(PendingIntent pendingIntent, String title) {
        NotificationManager.createNotificationChannel();

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

    private static void sendDailyReminderNotification(PendingIntent pendingIntent) {
        NotificationManager.createNotificationChannel();

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

    private static void sendWeekSummaryNotification(PendingIntent pendingIntent) {
        NotificationManager.createNotificationChannel();

        int numberOfWordAddedThisWeek = NotesUtils.countNumberOfWordThisWeek();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ProfLama.getAppContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(ProfLama.getAppContext().getString(R.string.app_name))
                .setContentText(ProfLama.getAppContext().getString(R.string.weeklyNotificationContent, numberOfWordAddedThisWeek))
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        android.app.NotificationManager notificationManager =
                (android.app.NotificationManager) ProfLama.getAppContext().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(2, builder.build());
    }

    public static void sendNotificationTriggerByAlarm(PendingIntent pendingIntent) {
        Calendar calendar = Calendar.getInstance();
        NotificationPreferences notificationPreferences = JsonFileRepository.getNotificationPreferences();

        if ( calendar.get(Calendar.DAY_OF_MONTH)  == notificationPreferences.getLastDailyNotificationDay()
                && calendar.get(Calendar.MONTH) == notificationPreferences.getLastDailyNotificationMonth()) {
            Logger.add("Do not sent, the alarm was already send today");
            return;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            sendWeekSummaryNotification(pendingIntent);
        } else {
            sendDailyReminderNotification(pendingIntent);
        }

        notificationPreferences.setLastDailyNotificationDay(calendar.get(Calendar.DAY_OF_MONTH));
        notificationPreferences.setLastDailyNotificationMonth(calendar.get(Calendar.MONTH));
        JsonFileRepository.saveNotificationPreferences(notificationPreferences);
    }

}
