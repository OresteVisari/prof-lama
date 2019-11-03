package fr.pbenoit.proflama.notifications;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Date;

import fr.pbenoit.proflama.activities.MainActivity;
import fr.pbenoit.proflama.services.NotesUtils;

@RequiresApi(api = Build.VERSION_CODES.N)
public class DailyNotificationJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Intent intent = new Intent(this, MainActivity.class);
        //intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        //intent.setData(Uri.parse("package:" + getPackageName()));
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        LocalNotifications.sendDailyReminderNotification(pendingIntent);
        NotesUtils.logJobScheduleTime(0, new Date());

        jobFinished(params, true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}

