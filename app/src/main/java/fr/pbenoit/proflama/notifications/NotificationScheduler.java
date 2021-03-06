package fr.pbenoit.proflama.notifications;

public class NotificationScheduler {

    private int lastDailyNotificationDay;

    private int lastDailyNotificationMonth;

    public NotificationScheduler() {
        this.lastDailyNotificationDay = -1;
        this.lastDailyNotificationMonth = -1;
    }

    public int getLastDailyNotificationDay() {
        return lastDailyNotificationDay;
    }

    public void setLastDailyNotificationDay(int lastDailyNotificationDay) {
        this.lastDailyNotificationDay = lastDailyNotificationDay;
    }

    public int getLastDailyNotificationMonth() {
        return lastDailyNotificationMonth;
    }

    public void setLastDailyNotificationMonth(int lastDailyNotificationMonth) {
        this.lastDailyNotificationMonth = lastDailyNotificationMonth;
    }
}
