package com.example.eecs4443project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewFlipper;

public class GamifiedLesson2 extends GamifiedLesson1{
    private ViewFlipper viewFlipper;
    private GestureDetector gestureDetector;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamified_lesson_2);

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
                Intent intent = new Intent(GamifiedLesson2.this, GamifiedLesson3.class);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamifiedLesson2.this, GamifiedLesson1.class);
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
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.sharpturn;
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
