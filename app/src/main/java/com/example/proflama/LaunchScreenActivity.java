package com.example.proflama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LaunchScreenActivity extends AppCompatActivity {

    private ImageView lamaIcon;

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
    }

    public void openMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
