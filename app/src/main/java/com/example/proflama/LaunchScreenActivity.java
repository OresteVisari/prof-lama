package com.example.proflama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class LaunchScreenActivity extends AppCompatActivity {

    private ImageView lamaIcon;

    private static final int DELAY = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        lamaIcon = findViewById(R.id.lamaIcon);
        lamaIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainScreen();
            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                openMainScreen();
                finish();
            }
        };

        new Handler().postDelayed(runnable, DELAY);
    }

    public void openMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
