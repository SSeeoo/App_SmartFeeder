package com.example.smartfeeder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedSettingsActivity extends AppCompatActivity {

    private SeekBar seekBarTimeInterval;
    private TextView tvTimeInterval;
    private TimePicker timePickerStart, timePickerEnd;
    private Button btnSetTimeInterval, btnSetBlockTime;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_settings);

        seekBarTimeInterval = findViewById(R.id.seekBarTimeInterval);
        tvTimeInterval = findViewById(R.id.tvTimeInterval);
        timePickerStart = findViewById(R.id.timePickerStart);
        timePickerEnd = findViewById(R.id.timePickerEnd);
        btnSetTimeInterval = findViewById(R.id.btnSetTimeInterval);
        btnSetBlockTime = findViewById(R.id.btnSetBlockTime);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        loadSettings();

        seekBarTimeInterval.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvTimeInterval.setText(progress + "분");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing here
            }
        });

        btnSetTimeInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings();
            }
        });

        btnSetBlockTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBlockTimeSettings();
            }
        });
    }

    private void loadSettings() {
        Call<SettingsResponse> call = apiInterface.getSettings();
        call.enqueue(new Callback<SettingsResponse>() {
            @Override
            public void onResponse(Call<SettingsResponse> call, Response<SettingsResponse> response) {
                if (response.isSuccessful()) {
                    // TODO: 서버로부터 받은 설정 값을 UI에 반영
                    // 예) tvTimeInterval.setText(response.body().getTimeInterval());
                }
            }

            @Override
            public void onFailure(Call<SettingsResponse> call, Throwable t) {
                // TODO: 에러 처리
            }
        });
    }

    private void saveSettings() {
        int timeInterval = seekBarTimeInterval.getProgress();

        ApiInterface.IntervalRequest intervalRequest = new ApiInterface.IntervalRequest("유저 아이디", timeInterval);
        apiInterface.setTimeInterval(intervalRequest).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // TODO: 응답 처리
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // TODO: 에러 처리
            }
        });
    }

    private void saveBlockTimeSettings() {
        int startHour, startMinute, endHour, endMinute;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            startHour = timePickerStart.getHour();
            startMinute = timePickerStart.getMinute();
            endHour = timePickerEnd.getHour();
            endMinute = timePickerEnd.getMinute();
        } else {
            startHour = timePickerStart.getCurrentHour();
            startMinute = timePickerStart.getCurrentMinute();
            endHour = timePickerEnd.getCurrentHour();
            endMinute = timePickerEnd.getCurrentMinute();
        }

        String blockStartTime = String.format("%02d:%02d", startHour, startMinute);
        String blockEndTime = String.format("%02d:%02d", endHour, endMinute);

        ApiInterface.TimeRestrictionRequest timeRestrictionRequest = new ApiInterface.TimeRestrictionRequest("유저 아이디", blockStartTime, blockEndTime);
        apiInterface.setTimeRestriction(timeRestrictionRequest).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // TODO: 응답 처리
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // TODO: 에러 처리
            }
        });
    }
}
