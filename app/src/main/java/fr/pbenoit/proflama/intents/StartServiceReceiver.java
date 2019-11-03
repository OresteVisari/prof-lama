package fr.pbenoit.proflama.intents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import fr.pbenoit.proflama.notifications.NotificationScheduler;

public class StartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationScheduler.scheduleDailyReportJob();
            NotificationScheduler.scheduleWeeklyReportJob();
        }
    }
}