package com.example.smartfeeder;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Locale; // 추가한 import 문

public class FeedSettingsActivity extends AppCompatActivity {

    private SeekBar seekBarTimeInterval;
    private TextView tvTimeInterval;
    private TimePicker timePickerStart, timePickerEnd;
    private ApiInterface apiInterface; // 클래스 변수로 다시 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_settings);

        seekBarTimeInterval = findViewById(R.id.seekBarTimeInterval);
        tvTimeInterval = findViewById(R.id.tvTimeInterval);
        timePickerStart = findViewById(R.id.timePickerStart);
        timePickerEnd = findViewById(R.id.timePickerEnd);
        Button btnSetTimeInterval = findViewById(R.id.btnSetTimeInterval);
        Button btnSetBlockTime = findViewById(R.id.btnSetBlockTime);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        loadSettings();

        seekBarTimeInterval.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvTimeInterval.setText(String.format(Locale.getDefault(), "%d분", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnSetTimeInterval.setOnClickListener(v -> saveSettings());
        btnSetBlockTime.setOnClickListener(v -> saveBlockTimeSettings());
    }

    private void loadSettings() {
        Call<SettingsResponse> call = apiInterface.getSettings();
        call.enqueue(new Callback<SettingsResponse>() {
            @Override
            public void onResponse(@NonNull Call<SettingsResponse> call, @NonNull Response<SettingsResponse> response) {
                if (response.isSuccessful()) {
                    // TODO: 서버로부터 받은 설정 값을 UI에 반영
                    // 예) tvTimeInterval.setText(response.body().getTimeInterval());
                } else {
                    // TODO: 에러 처리
                }
            }

            @Override
            public void onFailure(@NonNull Call<SettingsResponse> call, @NonNull Throwable t) {
                // TODO: 에러 처리
            }
        });
    }

    private void saveSettings() {
        int timeInterval = seekBarTimeInterval.getProgress();

        ApiInterface.IntervalRequest intervalRequest = new ApiInterface.IntervalRequest("유저 아이디", timeInterval);
        apiInterface.setTimeInterval(intervalRequest).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                // TODO: 응답 처리
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                // TODO: 에러 처리
            }
        });
    }

    private void saveBlockTimeSettings() {
        int startHour = timePickerStart.getHour();
        int startMinute = timePickerStart.getMinute();
        int endHour = timePickerEnd.getHour();
        int endMinute = timePickerEnd.getMinute();

        String blockStartTime = String.format(Locale.getDefault(), "%02d:%02d", startHour, startMinute);
        String blockEndTime = String.format(Locale.getDefault(), "%02d:%02d", endHour, endMinute);

        ApiInterface.TimeRestrictionRequest timeRestrictionRequest = new ApiInterface.TimeRestrictionRequest("유저 아이디", blockStartTime, blockEndTime);
        apiInterface.setTimeRestriction(timeRestrictionRequest).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                // TODO: 응답 처리
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                // TODO: 에러 처리
            }
        });
    }
}
