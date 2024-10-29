package com.example.eecs4443project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class StartLesson2Gamified extends GamifiedTest1Q3{
    private static final String MYDEBUG = "MYDEBUG";
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MYDEBUG, "Got here!");
        setContentView(R.layout.end_test1_gamified);

        Button yesButton = findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAndResetTimer();
                startTimer();

                Intent intent = new Intent(StartLesson2Gamified.this, GamifiedLesson6.class);
                startActivity(intent);
            }
        });

        Button noButton = findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartLesson2Gamified.this, GamifiedTest1Q1.class);
                startActivity(intent);
            }
        });
    }

    private void stopAndResetTimer() {
        timerHandler.removeCallbacks(timerRunnable);
        long elapsedTimeMillis = System.currentTimeMillis() - Timer.getStartTime(this);
        String elapsedTimeStr = Long.toString(elapsedTimeMillis);
        int seconds = (int) (elapsedTimeMillis / 1000) % 60;
        int minutes = (int) ((elapsedTimeMillis / (1000*60)) % 60);
        String elapsedTimeFormatted = String.format("%d min, %d sec", minutes, seconds);
        Timer.saveTimerData(this, elapsedTimeFormatted);
        Log.i(MYDEBUG, "Gamified Test 1 Timer. Elapsed time: " + elapsedTimeFormatted);
        Timer.setStartTime(this, 0);
        Timer.setTimerRunning(this, false);
    }

    private void startTimer() {
        Timer.setStartTime(this, System.currentTimeMillis());
        Timer.setTimerRunning(this, true);

        if (timerRunnable == null) {
            timerRunnable = new Runnable() {
                @Override
                public void run() {
                    if (Timer.isTimerRunning(StartLesson2Gamified.this)) {
                        long elapsedMillis = System.currentTimeMillis() - Timer.getStartTime(StartLesson2Gamified.this);
                        int seconds = (int) (elapsedMillis / 1000) % 60;
                        int minutes = (int) ((elapsedMillis / (1000*60)) % 60);
                        timerHandler.postDelayed(this, 1000);
                    }
                }
            };
        }
        timerHandler.postDelayed(timerRunnable, 0);
    }
}
