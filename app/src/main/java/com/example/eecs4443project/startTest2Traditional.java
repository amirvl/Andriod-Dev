package com.example.eecs4443project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class startTest2Traditional extends TraditionalLesson6{
    private static final String MYDEBUG= "MYDEBUG";

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
        setContentView(R.layout.start_test2_traditional);

        Button yesButton = findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAndResetTimer();

                Intent intent = new Intent(startTest2Traditional.this, startTest2TraditionalSure.class);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.noButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(startTest2Traditional.this, TraditionalLesson4.class);
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
        Log.i(MYDEBUG, "Lesson 2 Timer. Elapsed time: " + elapsedTimeFormatted);
        Timer.setStartTime(this, 0);
        Timer.setTimerRunning(this, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Timer.isTimerRunning(this)) {
            timerRunnable.run();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }
}
