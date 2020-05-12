package fr.pbenoit.proflama.notifications;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

import fr.pbenoit.proflama.NotificationAlarmReceiver;
import fr.pbenoit.proflama.ProfLama;
import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.activities.MainActivity;
import fr.pbenoit.proflama.repositories.JsonFileRepository;
import fr.pbenoit.proflama.utilities.NotesUtils;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationManager {

    private static final String CHANNEL_ID = "channel_prof_lama";

    public static void disableDisplayedNotification(final MainActivity activity) {
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) activity.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void scheduleNotificationAlarm(final MainActivity activity) {
        AlarmManager alarmManager = activity.getSystemService(AlarmManager.class);
        int request_code = 0;

        Calendar dateToSchedule = Calendar.getInstance();
        dateToSchedule.set(Calendar.HOUR_OF_DAY, 20);
        dateToSchedule.set(Calendar.MINUTE, 0);
        dateToSchedule.set(Calendar.SECOND, 0);

        Calendar now = Calendar.getInstance();
        if (now.after(dateToSchedule)) {
            dateToSchedule.add(Calendar.DATE, 1);
        }

        Intent intent = new Intent(activity, NotificationAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ProfLama.getAppContext(), request_code, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC, dateToSchedule.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private static void createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        android.app.NotificationManager notificationManager = ProfLama.getAppContext().getSystemService(android.app.NotificationManager.class);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, ProfLama.getAppContext().getString(R.string.app_name), android.app.NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Prof Lama notifications");
        notificationManager.createNotificationChannel(channel);
    }

    public static void sendWordCreationNotification(PendingIntent pendingIntent, String title) {
        NotificationManager.createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ProfLama.getAppContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(ProfLama.getAppContext().getString(R.string.app_name))
                .setContentText(ProfLama.getAppContext().getString(R.string.pushNotificationNewTitleContent)+ title)
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        android.app.NotificationManager notificationManager =
                (android.app.NotificationManager) ProfLama.getAppContext().getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    private static void sendWeekSummaryNotification(PendingIntent pendingIntent) {
        NotificationManager.createNotificationChannel();

        String content;
        int numberOfWordAddedThisWeek = NotesUtils.countNumberOfWordThisWeek();
        if (numberOfWordAddedThisWeek > 0 ) {
            content = ProfLama.getAppContext().getString(R.string.weeklyNotificationContent, numberOfWordAddedThisWeek);
        } else {
            content = ProfLama.getAppContext().getString(R.string.weeklyNotificationContentWhenNoWordAdded);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ProfLama.getAppContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(ProfLama.getAppContext().getString(R.string.app_name))
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        android.app.NotificationManager notificationManager =
                (android.app.NotificationManager) ProfLama.getAppContext().getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(2, builder.build());
    }

    public static void sendNotificationTriggerByAlarm(PendingIntent pendingIntent) {
        Calendar calendar = Calendar.getInstance();
        NotificationScheduler notificationScheduler = JsonFileRepository.getNotificationPreferences();

        if ( calendar.get(Calendar.DAY_OF_MONTH)  == notificationScheduler.getLastDailyNotificationDay()
                && calendar.get(Calendar.MONTH) == notificationScheduler.getLastDailyNotificationMonth()) {
            return;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            sendWeekSummaryNotification(pendingIntent);
        }

        notificationScheduler.setLastDailyNotificationDay(calendar.get(Calendar.DAY_OF_MONTH));
        notificationScheduler.setLastDailyNotificationMonth(calendar.get(Calendar.MONTH));
        JsonFileRepository.saveNotificationPreferences(notificationScheduler);
    }

}
