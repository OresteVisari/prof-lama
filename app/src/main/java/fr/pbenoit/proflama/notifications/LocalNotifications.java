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
        notificationManager.notify(0 /* ID of notification */, builder.build());
    }

    public static void sendDailyReminderNotification(PendingIntent pendingIntent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ProfLama.getAppContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(ProfLama.getAppContext().getString(R.string.app_name))
                .setContentText("Poświęć 5 minut na przejrzenie kilku słów ;)")
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        android.app.NotificationManager notificationManager =
                (android.app.NotificationManager) ProfLama.getAppContext().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1 /* ID of notification */, builder.build());
    }

    public static void sendWeekSummaryNotification(PendingIntent pendingIntent, int numberOfWord) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ProfLama.getAppContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(ProfLama.getAppContext().getString(R.string.app_name))
                .setContentText("Brawo, nauczyłeś się " + numberOfWord + " nowych słów w tym tygodniu")
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        android.app.NotificationManager notificationManager =
                (android.app.NotificationManager) ProfLama.getAppContext().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(2 /* ID of notification */, builder.build());
    }

    public static void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //CharSequence name = getString(R.string.channel_name);
            //String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Tltle A", importance);
            channel.setDescription("Description D");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = ProfLama.getAppContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
