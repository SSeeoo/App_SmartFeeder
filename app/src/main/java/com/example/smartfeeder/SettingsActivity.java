// SettingsActivity.java
package com.example.smartfeeder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.switchmaterial.SwitchMaterial; // SwitchMaterial import

public class SettingsActivity extends AppCompatActivity {
    private SwitchMaterial motorNotificationSwitch; // Switch to SwitchMaterial

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        motorNotificationSwitch = findViewById(R.id.motorNotificationSwitch);

        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        boolean isNotificationOn = prefs.getBoolean("motor_notification", false);
        motorNotificationSwitch.setChecked(isNotificationOn);

        motorNotificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
            editor.putBoolean("motor_notification", isChecked);
            editor.apply();

            // 사용자에게 설정이 저장되었다는 피드백 제공
            Toast.makeText(SettingsActivity.this, "Settings saved", Toast.LENGTH_SHORT).show();
        });
    }
}
