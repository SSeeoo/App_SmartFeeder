package com.example.smartfeeder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private Switch motorNotificationSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings); // 사용하는 layout 파일 이름 설정

        motorNotificationSwitch = findViewById(R.id.motorNotificationSwitch);

        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        boolean isNotificationOn = prefs.getBoolean("motor_notification", false); // Default is false
        motorNotificationSwitch.setChecked(isNotificationOn);

        motorNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
                editor.putBoolean("motor_notification", isChecked);
                editor.apply();
            }
        });
    }
}
