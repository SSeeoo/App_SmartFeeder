package com.example.smartfeeder;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationHelper.createNotificationChannel(this);

        FrameLayout profileBlock = findViewById(R.id.profileBlock);
        FrameLayout timerBlock = findViewById(R.id.timerBlock);
        FrameLayout feedBlock = findViewById(R.id.feedBlock);
        FrameLayout cameraBlock = findViewById(R.id.cameraBlock);
        FrameLayout historyBlock = findViewById(R.id.historyBlock);
        FrameLayout settingsBlock = findViewById(R.id.settingsBlock);

        profileBlock.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        timerBlock.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FeedSettingsActivity.class);
            startActivity(intent);
        });

        feedBlock.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FeedControlActivity.class);
            startActivity(intent);
        });

        cameraBlock.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CameraActivity.class);
            startActivity(intent);
        });

        historyBlock.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        settingsBlock.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}
