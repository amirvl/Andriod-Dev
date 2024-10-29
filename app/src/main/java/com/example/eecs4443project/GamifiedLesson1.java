package com.example.eecs4443project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

public class GamifiedLesson1 extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private GestureDetector gestureDetector;

    TextView timerTextView;
    long startTime = 0;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (!Timer.isTimerRunning(GamifiedLesson1.this)) {
                timerHandler.removeCallbacks(this);
                return;
            }

            long millis = System.currentTimeMillis() - Timer.getStartTime(GamifiedLesson1.this);
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamified_lesson_1);

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        if (Timer.isTimerRunning(this)) {
            timerRunnable.run();
        }

        viewFlipper = findViewById(R.id.viewFlipper);
        final VideoView videoView = findViewById(R.id.videoView);

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamifiedLesson1.this, GamifiedLesson2.class);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamifiedLesson1.this, MainActivity.class);
                startActivity(intent);
            }
        });

        gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getY() - e2.getY() > 50 && Math.abs(velocityY) > 100) {
                    if (viewFlipper.getDisplayedChild() == 0) {
                        viewFlipper.showNext();
                    }
                    return true;
                } else if (e2.getY() - e1.getY() > 50 && Math.abs(velocityY) > 100) {
                    if (viewFlipper.getDisplayedChild() == 1) {
                        viewFlipper.showPrevious();
                        videoView.seekTo(0);
                        videoView.start();
                    }
                    return true;
                }
                return false;
            }
        });
        loadVideo();
    }

    private void loadVideo() {
        VideoView videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.lane_merge;
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
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
