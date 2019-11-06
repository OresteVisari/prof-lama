package fr.pbenoit.proflama.notifications;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Calendar;

import fr.pbenoit.proflama.activities.MainActivity;
import fr.pbenoit.proflama.services.NotesUtils;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NotificationJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        NotesUtils.addLog("start notification job");

        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR_OF_DAY) == 23) {
            NotesUtils.addLog("notification job  - need to send a notification");
            NotificationManager.sendNotificationTriggerByAlarm(pendingIntent);
        } else {
            NotesUtils.addLog("notification job  - not the expected time: " + calendar.get(Calendar.HOUR_OF_DAY));
        }

        jobFinished(params, true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}

