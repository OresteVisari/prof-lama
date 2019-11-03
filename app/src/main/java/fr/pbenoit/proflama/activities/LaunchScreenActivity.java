package fr.pbenoit.proflama.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Date;

import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.services.NotesUtils;

public class LaunchScreenActivity extends AppCompatActivity {

    private static final int DELAY = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        NotesUtils.addLog("App start at " + new Date());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        new Handler().postDelayed(runnable, DELAY);
    }

}
