package fr.pbenoit.proflama.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.utilities.Logger;

public class LaunchScreenActivity extends AppCompatActivity {

    private static final int DELAY = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        Logger.add("Prof Lama start");

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
