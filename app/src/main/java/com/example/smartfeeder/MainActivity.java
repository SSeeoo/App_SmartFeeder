package com.example.smartfeeder;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationHelper.createNotificationChannel(this); // 알림 채널 생성 코드 추가

        // 각 프레임 레이아웃에 대한 참조 설정
        FrameLayout profileBlock = findViewById(R.id.profileBlock);
        FrameLayout timerBlock = findViewById(R.id.timerBlock);
        FrameLayout feedBlock = findViewById(R.id.feedBlock);
        FrameLayout cameraBlock = findViewById(R.id.cameraBlock);
        FrameLayout historyBlock = findViewById(R.id.historyBlock);
        FrameLayout settingsBlock = findViewById(R.id.settingsBlock);

        // Profile 블록을 클릭했을 때
        profileBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        // Timer 블록을 클릭했을 때
        timerBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FeedSettingsActivity.class);
                startActivity(intent);
            }
        });

        // Feed 블록을 클릭했을 때
        feedBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FeedControlActivity.class);
                startActivity(intent);
            }
        });

        // Camera 블록을 클릭했을 때
        cameraBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        // History 블록을 클릭했을 때
        historyBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        // Settings 블록을 클릭했을 때
        settingsBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
