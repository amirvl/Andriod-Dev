package com.example.eecs4443project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button traditionalButton = findViewById(R.id.traditionalButton);
        traditionalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Timer.isTimerRunning(MainActivity.this)) {
                    Timer.setStartTime(MainActivity.this, System.currentTimeMillis());
                    Timer.setTimerRunning(MainActivity.this, true);
                    startTimer();
                }

                Intent intent = new Intent(MainActivity.this, TraditionalLesson1.class);
                startActivity(intent);
            }
        });

        Button gamifiedButton = findViewById(R.id.gamifiedButton);
        gamifiedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Timer.isTimerRunning(MainActivity.this)) {
                    Timer.setStartTime(MainActivity.this, System.currentTimeMillis());
                    Timer.setTimerRunning(MainActivity.this, true);
                    startTimer();
                }

                Intent intent = new Intent(MainActivity.this, GamifiedLesson1.class);
                startActivity(intent);
            }
        });
    }

    private void startTimer() {
        timerRunnable.run();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Timer.isTimerRunning(this)) {
            startTimer();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Timer.setTimerRunning(this, false);
    }
}
