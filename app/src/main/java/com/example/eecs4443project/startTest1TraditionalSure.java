package com.example.eecs4443project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class startTest1TraditionalSure extends startTest1Traditional {
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
        setContentView(R.layout.start_test2_traditional_sure);

        Counter.resetWrongAnswerCount(this,"Question 1");
        Counter.resetWrongAnswerCount(this,"Question 2");
        Counter.resetWrongAnswerCount(this,"Question 3");


        Button yesButton = findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();

                Intent intent = new Intent(startTest1TraditionalSure.this, TraditionalTest1Q1.class);
                startActivity(intent);
            }
        });

        Button noButton = findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(startTest1TraditionalSure.this, TraditionalLesson3.class);
                startActivity(intent);
            }
        });
    }

    private void startTimer() {
        Timer.setStartTime(this, System.currentTimeMillis());
        Timer.setTimerRunning(this, true);

        if (timerRunnable == null) {
            timerRunnable = new Runnable() {
                @Override
                public void run() {
                    if (Timer.isTimerRunning(startTest1TraditionalSure.this)) {
                        long elapsedMillis = System.currentTimeMillis() - Timer.getStartTime(startTest1TraditionalSure.this);
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

