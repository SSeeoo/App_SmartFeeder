package com.example.smartfeeder;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FeedControlActivity extends AppCompatActivity {

    private Button feedButton;
    private NumberPicker numberPicker;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_control);

        feedButton = findViewById(R.id.feedButton);
        numberPicker = findViewById(R.id.numberPicker);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(60); // 예를 들어, 1분에서 60분 사이의 값

        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 먹이 사출 기능 호출
                dispenseFeed();

                // 타이머 설정
                int minutes = numberPicker.getValue();
                startTimer(minutes * 60 * 1000); // 밀리초로 변환
            }
        });
    }

    private void dispenseFeed() {
        // Retrofit을 사용하여 서버에 feedDispense 요청을 보냅니다.
        //
        // 응답을 받으면, Toast 메시지를 표시합니다.


        Toast.makeText(this, "Feed dispensed", Toast.LENGTH_SHORT).show();
    }

    private void startTimer(long millisInFuture) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // 각 틱마다 UI를 업데이트할 수 있습니다.
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
