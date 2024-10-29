package com.example.eecs4443project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class EndTraditionalLesson extends TraditionalTest2Q2{
    private static final String MYDEBUG = "MYDEBUG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MYDEBUG, "Got here!");
        setContentView(R.layout.end_test2_traditional);

        Button yesButton = findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAndResetTimer();

                Intent intent = new Intent(EndTraditionalLesson.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button noButton = findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EndTraditionalLesson.this, TraditionalTest2Q2.class);
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
        Log.i(MYDEBUG, "Traditional Test 2 Timer. Elapsed time: " + elapsedTimeFormatted);
        Timer.setStartTime(this, 0);
        Timer.setTimerRunning(this, false);
    }
}
