package fr.pbenoit.proflama;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import fr.pbenoit.proflama.activities.MainActivity;
import fr.pbenoit.proflama.notifications.NotificationManager;
import fr.pbenoit.proflama.services.NotesUtils;

public class NotificationAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotesUtils.addLog("NotificationAlarmReceiver: send notification");

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        NotificationManager.sendNotificationTriggerByAlarm(pendingIntent);
    }
}