package fr.pbenoit.proflama.notifications;

import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

import fr.pbenoit.proflama.ProfLama;
import fr.pbenoit.proflama.activities.MainActivity;

public class DailyNotificationJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Intent intent = new Intent(ProfLama.getAppContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
        LocalNotifications.sendDailyReminderNotification(pendingIntent);
        NotificationScheduler.scheduleDailyReportJob(getApplicationContext());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}

