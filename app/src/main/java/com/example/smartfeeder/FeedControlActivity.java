package com.example.smartfeeder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedControlActivity extends AppCompatActivity {

    private NumberPicker hourPicker, minutePicker;
    private Button feedButton;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_control);

        feedButton = findViewById(R.id.feedButton);
        hourPicker = findViewById(R.id.hourPicker);
        minutePicker = findViewById(R.id.minutePicker);

        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(24);
        hourPicker.setWrapSelectorWheel(true);

        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setWrapSelectorWheel(true);

        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispenseFeed();
                int hours = hourPicker.getValue();
                int minutes = minutePicker.getValue();
                startTimer((hours * 60 + minutes) * 60 * 1000); // 밀리초로 변환
            }
        });
    }

    private void dispenseFeed() {
        // 0시간 0분으로 설정된 경우, 요청을 보내지 않습니다.
        // Retrofit 인스턴스를 생성하고, 요청을 보냅니다.
        int hours = hourPicker.getValue();
        int minutes = minutePicker.getValue();

        if (hours == 0 && minutes == 0) {
            Toast.makeText(this, "Cannot dispense feed for 0 hours and 0 minutes", Toast.LENGTH_SHORT).show();
            return;
        }

        int totalMinutes = (hours * 60) + minutes;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.5:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SmartFeederApi api = retrofit.create(SmartFeederApi.class);
        Call<Void> call = api.dispenseFeed(totalMinutes);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FeedControlActivity.this, "Feed dispensed", Toast.LENGTH_SHORT).show();
                    // Check SharedPreferences and Send Notification if needed
                    sendNotificationIfNeeded(); // <- 이 부분이 알림을 보내는 코드를 호출하는 부분입니다.
                } else {
                    Toast.makeText(FeedControlActivity.this, "Failed to dispense feed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(FeedControlActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 이 함수를 추가하여 SharedPreferences를 확인하고 필요하다면 알림을 보냅니다.
    private void sendNotificationIfNeeded() {
        // TODO: Change "Settings" and "motor_notification" to the actual SharedPreferences and key you are using
        boolean isNotificationOn = getSharedPreferences("Settings", MODE_PRIVATE).getBoolean("motor_notification", false);

        if (isNotificationOn) {
            sendNotification();
        }
    }

    // 이 함수를 추가하여 실제로 알림을 보냅니다.
    private void sendNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "feed_notification_channel";
        String channelName = "Feeder Notification";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher) // Set your notification icon here
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
                // UI 업데이트를 여기에 추가할 수 있습니다.
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
