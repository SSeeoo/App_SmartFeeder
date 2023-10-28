package com.example.smartfeeder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException;

public class FeedControlActivity extends AppCompatActivity {

    private NumberPicker hourPicker, minutePicker, secondPicker;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_control);

        Button feedButton = findViewById(R.id.feedButton);
        hourPicker = findViewById(R.id.hourPicker);
        minutePicker = findViewById(R.id.minutePicker);
        secondPicker = findViewById(R.id.secondPicker);

        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(24);
        hourPicker.setWrapSelectorWheel(true);

        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setWrapSelectorWheel(true);

        secondPicker.setMinValue(0);
        secondPicker.setMaxValue(59);
        secondPicker.setWrapSelectorWheel(true);

        feedButton.setOnClickListener(v -> {
            dispenseFeed();
            long hours = hourPicker.getValue();
            long minutes = minutePicker.getValue();
            long seconds = secondPicker.getValue();
            startTimer((hours * 3600L + minutes * 60L + seconds) * 1000L);
        });
    }

    private void dispenseFeed() {
        long hours = hourPicker.getValue();
        long minutes = minutePicker.getValue();
        long seconds = secondPicker.getValue();

        long totalMillis = ((hours * 3600L) + (minutes * 60L) + seconds) * 1000L;

        SmartFeederApi api = RetrofitClient.getRetrofitInstance().create(SmartFeederApi.class);

        ControlMotorRequest request = new ControlMotorRequest(false, totalMillis);
        Call<Void> call = api.controlMotor(request);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FeedControlActivity.this, "Feed dispensed", Toast.LENGTH_SHORT).show();
                    sendNotificationIfNeeded();
                    startTimer(totalMillis);
                } else {
                    String errorMessage = "Failed to dispense feed";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage += ": " + response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            response.errorBody().close();
                        }
                    }
                    Toast.makeText(FeedControlActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(FeedControlActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendNotificationIfNeeded() {
        boolean isNotificationOn = getSharedPreferences("Settings", MODE_PRIVATE).getBoolean("motor_notification", false);

        if (isNotificationOn) {
            sendNotification();
        }
    }

    private void sendNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "feed_notification_channel";
        String channelName = "Feeder Notification";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Feed Dispensed")
                .setContentText("The feeder has dispensed the feed.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, builder.build());
    }

    private void startTimer(long millisInFuture) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Toast.makeText(FeedControlActivity.this, "Timer finished", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
