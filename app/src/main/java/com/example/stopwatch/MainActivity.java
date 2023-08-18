package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView watchTimer;
    Button buttonStart, buttonStop, buttonReset;

    private Handler handler;
    private long startTime = 0L;
    private long elapsedTime = 0L;

    private Runnable timerRunnable = new Runnable() {
        public void run() {
            long millis = System.currentTimeMillis() - startTime + elapsedTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;
            seconds %= 60;
            minutes %= 60;

            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            watchTimer.setText(timeString);

            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        watchTimer=(TextView) findViewById(R.id.textView_timer);
        buttonStart=(Button) findViewById(R.id.button_start);
        buttonStop=(Button) findViewById(R.id.button_stop);
        buttonReset=(Button) findViewById(R.id.button_reset);

        handler = new Handler();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
                handler.postDelayed(timerRunnable, 0);
                buttonStart.setEnabled(false);
                buttonStop.setEnabled(true);
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elapsedTime += System.currentTimeMillis() - startTime;
                handler.removeCallbacks(timerRunnable);
                buttonStart.setEnabled(true);
                buttonStop.setEnabled(false);
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elapsedTime = 0L;
                watchTimer.setText("00:00:00");
                buttonStart.setEnabled(true);
                buttonStop.setEnabled(true);
            }
        });
    }


}