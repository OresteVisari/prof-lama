package fr.pbenoit.proflama.notifications;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

public class NotificationScheduler {

    private static final long ONE_DAY_INTERVAL = 24 * 60 * 60 * 1000L;

    private static final long ONE_WEEK_INTERVAL = 7 * 24 * 60 * 60 * 1000L;

    public static void scheduleDailyReportJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, DailyNotificationJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setPeriodic(ONE_DAY_INTERVAL);
        builder.setRequiresCharging(false);
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }

    public static void scheduleWeeklyReportJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, WeeklyNotificationJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(1, serviceComponent);
        builder.setPeriodic(ONE_WEEK_INTERVAL);
        builder.setRequiresCharging(false);
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }

}