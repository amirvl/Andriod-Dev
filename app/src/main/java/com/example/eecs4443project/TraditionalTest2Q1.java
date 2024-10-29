package com.example.eecs4443project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class TraditionalTest2Q1 extends startTest2Traditional {
    private static final String MYDEBUG = "MYDEBUG";
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;
    long startTime = 0;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (!Timer.isTimerRunning(TraditionalTest2Q1.this)) {
                timerHandler.removeCallbacks(this);
                return;
            }

            long millis = System.currentTimeMillis() - Timer.getStartTime(TraditionalTest2Q1.this);
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MYDEBUG, "Got here!");
        setContentView(R.layout.traditional_test2_q1);

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        if (Timer.isTimerRunning(this)) {
            timerRunnable.run();
        }

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (TraditionalTest2Q1.this, TraditionalTest2Q2.class);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TraditionalTest2Q1.this, startTest2Traditional.class);
                startActivity(intent);
            }
        });

        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);
        radioButton5 = findViewById(R.id.radioButton5);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAnswers();
            }
        });
    }

    private void validateAnswers() {
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == R.id.radioButton1) {
            radioButton1.setTextColor(Color.GREEN);
            resetIncorrectRadioButtons(selectedId);
            radioGroup.setEnabled(false);
        } else {
            RadioButton selectedRadioButton = findViewById(selectedId);
            if (selectedRadioButton != null) {
                selectedRadioButton.setTextColor(Color.RED);
                Counter.incrementWrongAnswerCount(this, "Question 1");
                int wrongAnswerCounter = Counter.getWrongAnswerCount(this, "Question 1");
                Log.i(MYDEBUG, "You've answered Test 2 Question 1 incorrectly " + wrongAnswerCounter + " times.");
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    resetRadioButtons();
                    radioGroup.clearCheck();
                    radioGroup.setEnabled(true);
                }
            }, 2000);
        }
    }


    private void resetIncorrectRadioButtons(int correctRadioButtonId) {
        if (radioButton2.getId() != correctRadioButtonId) {
            radioButton2.setTextColor(Color.BLACK);
        }
        if(radioButton3.getId() != correctRadioButtonId) {
            radioButton3.setTextColor(Color.BLACK);
        }
        if (radioButton4.getId() != correctRadioButtonId) {
            radioButton4.setTextColor(Color.BLACK);
        }
        if (radioButton5.getId() != correctRadioButtonId) {
            radioButton5.setTextColor(Color.BLACK);
        }
    }

    private void resetRadioButtons() {
        radioButton1.setTextColor(Color.WHITE);
        radioButton2.setTextColor(Color.WHITE);
        radioButton3.setTextColor(Color.WHITE);
        radioButton4.setTextColor(Color.WHITE);
        radioButton5.setTextColor(Color.WHITE);
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
