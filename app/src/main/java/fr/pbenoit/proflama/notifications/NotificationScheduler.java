package fr.pbenoit.proflama.notifications;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import fr.pbenoit.proflama.ProfLama;

public class NotificationScheduler  {

    private static final long ONE_DAY_INTERVAL = 24 * 60 * 60 * 1000L;

    private static final long ONE_WEEK_INTERVAL = 7 * 24 * 60 * 60 * 1000L;

    private static final long SHORT_INTERVAL =  20 * 60 * 1000L;

    private static final long MEDIUM_INTERVAL = 30 * 60 * 1000L;

    private static final int DAILY_JOB_ID = 0;

    private static final int WEEKLY_JOB_ID = 1;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void scheduleDailyReportJob() {
        JobScheduler jobScheduler = ProfLama.getAppContext().getSystemService(JobScheduler.class);

        if (jobScheduler.getPendingJob(DAILY_JOB_ID) != null) {
            return;
        }

        ComponentName serviceComponent = new ComponentName(ProfLama.getAppContext(), DailyNotificationJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(DAILY_JOB_ID, serviceComponent);
        //builder.setPeriodic(SHORT_INTERVAL);
        builder.setPeriodic(SHORT_INTERVAL);
        jobScheduler.schedule(builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void scheduleWeeklyReportJob() {
        JobScheduler jobScheduler = ProfLama.getAppContext().getSystemService(JobScheduler.class);

        if (jobScheduler.getPendingJob(WEEKLY_JOB_ID) != null) {
            return;
        }

        ComponentName serviceComponent = new ComponentName(ProfLama.getAppContext(), WeeklyNotificationJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(WEEKLY_JOB_ID, serviceComponent);
        //builder.setPeriodic(ONE_WEEK_INTERVAL);
        builder.setPeriodic(MEDIUM_INTERVAL);
        jobScheduler.schedule(builder.build());
    }

}