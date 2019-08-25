package fr.pbenoit.proflama;

import android.app.Application;
import android.content.Context;

public class ProfLama extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        ProfLama.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ProfLama.context;
    }
}
