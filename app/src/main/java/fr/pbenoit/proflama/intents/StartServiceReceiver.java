package fr.pbenoit.proflama.intents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import fr.pbenoit.proflama.notifications.NotificationScheduler;

public class StartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationScheduler.scheduleDailyReportJob(context);
        NotificationScheduler.scheduleWeeklyReportJob(context);
    }
}