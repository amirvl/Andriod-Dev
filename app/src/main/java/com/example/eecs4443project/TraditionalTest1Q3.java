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

public class TraditionalTest1Q3 extends TraditionalTest1Q2 {
    private static final String MYDEBUG = "MYDEBUG";
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MYDEBUG, "Got here!");
        setContentView(R.layout.traditional_test1_q3);

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        if (Timer.isTimerRunning(this)) {
            timerRunnable.run();
        }

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (TraditionalTest1Q3.this, StartLesson2Traditional.class);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (TraditionalTest1Q3.this, TraditionalTest1Q2.class);
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

        if (selectedId == R.id.radioButton4) {
            radioButton4.setTextColor(Color.GREEN);
            resetIncorrectRadioButtons(selectedId);
            radioGroup.setEnabled(false);
        } else {
            RadioButton selectedRadioButton = findViewById(selectedId);
            if (selectedRadioButton != null) {
                selectedRadioButton.setTextColor(Color.RED);
                Counter.incrementWrongAnswerCount(this, "Question 3");
                int wrongAnswerCounter = Counter.getWrongAnswerCount(this, "Question 3");
                Log.i(MYDEBUG, "You've answered Test 1 Question 3 incorrectly " + wrongAnswerCounter + " times.");
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
        if (radioButton1.getId() != correctRadioButtonId) {
            radioButton1.setTextColor(Color.BLACK);
        }
        if (radioButton2.getId() != correctRadioButtonId) {
            radioButton2.setTextColor(Color.BLACK);
        }
        if(radioButton3.getId() != correctRadioButtonId) {
            radioButton3.setTextColor(Color.BLACK);
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