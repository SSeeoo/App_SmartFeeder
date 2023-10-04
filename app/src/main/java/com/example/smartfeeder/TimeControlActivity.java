package com.example.smartfeeder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;

public class TimeControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_settings);

        SeekBar seekBarTimeInterval = findViewById(R.id.seekBarTimeInterval);
        TextView tvTimeInterval = findViewById(R.id.tvTimeInterval);
        Button btnSetTimeInterval = findViewById(R.id.btnSetTimeInterval);
        TimePicker timePickerStart = findViewById(R.id.timePickerStart);
        TimePicker timePickerEnd = findViewById(R.id.timePickerEnd);
        Button btnSetBlockTime = findViewById(R.id.btnSetBlockTime);

        // SeekBar Listener
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
            public void onClick(View v) {
                // Handle time interval set action
            }
        });

        btnSetBlockTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle block time set action
            }
        });
    }
}
