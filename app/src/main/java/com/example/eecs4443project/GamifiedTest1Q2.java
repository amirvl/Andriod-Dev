package com.example.eecs4443project;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class GamifiedTest1Q2 extends GamifiedTest1Q1{
    private static final String MYDEBUG = "MYDEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MYDEBUG, "Got here!");
        setContentView(R.layout.gamified_test1_q2);

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        if (Timer.isTimerRunning(this)) {
            timerRunnable.run();
        }

        VideoView videoView1 = findViewById(R.id.videoView1);
        VideoView videoView2 = findViewById(R.id.videoView2);
        VideoView videoView3 = findViewById(R.id.videoView3);

        videoView1.setFocusable(false);
        videoView2.setFocusable(false);
        videoView3.setFocusable(false);

        videoView1.setOnClickListener(this::onChoiceClick);
        videoView2.setOnClickListener(this::onChoiceClick);
        videoView3.setOnClickListener(this::onChoiceClick);

        setupVideoView(R.id.videoView1, R.raw.l1q2a1);
        setupVideoView(R.id.videoView2, R.raw.l1q2a2);
        setupVideoView(R.id.videoView3, R.raw.l1q2a3);

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamifiedTest1Q2.this, GamifiedTest1Q3.class);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamifiedTest1Q2.this, GamifiedTest1Q1.class);
                startActivity(intent);
            }
        });
    }

    public void onChoiceClick(View view) {
        boolean isAnswerCorrect = "correct".equals(view.getTag());

        int topLeftDrawable, topRightDrawable, bottomLeftDrawable, bottomRightDrawable;

        if (isAnswerCorrect) {
            topLeftDrawable = R.drawable.green_top_left_corner;
            topRightDrawable = R.drawable.green_top_right_corner;
            bottomLeftDrawable = R.drawable.green_bottom_left_corner;
            bottomRightDrawable = R.drawable.green_bottom_right_corner;
        }else {
            topLeftDrawable = R.drawable.red_top_left_corner;
            topRightDrawable = R.drawable.red_top_right_corner;
            bottomLeftDrawable = R.drawable.red_bottom_left_corner;
            bottomRightDrawable = R.drawable.red_bottom_right_corner;
        }

        View topLeftCorner = findViewById(R.id.bottomRightCorner);
        View topRightCorner = findViewById(R.id.bottomLeftCorner);
        View bottomLeftCorner = findViewById(R.id.topRightCorner);
        View bottomRightCorner = findViewById(R.id.topLeftCorner);

        topLeftCorner.setBackgroundResource(topLeftDrawable);
        topRightCorner.setBackgroundResource(topRightDrawable);
        bottomLeftCorner.setBackgroundResource(bottomLeftDrawable);
        bottomRightCorner.setBackgroundResource(bottomRightDrawable);

        topLeftCorner.setVisibility(View.VISIBLE);
        topRightCorner.setVisibility(View.VISIBLE);
        bottomLeftCorner.setVisibility(View.VISIBLE);
        bottomRightCorner.setVisibility(View.VISIBLE);

        if (!"correct".equals(view.getTag())) {
            Counter.incrementWrongAnswerCount(this, "Question 2");
            int wrongAnswerCounter = Counter.getWrongAnswerCount(this, "Question 2");
            Log.i(MYDEBUG, "You've answered Gamified Test 1 Question 2 incorrectly " + wrongAnswerCounter + " times.");

            View rootView = findViewById(android.R.id.content);
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            rootView.startAnimation(shake);
        }

        new Handler().postDelayed(() -> {
            topLeftCorner.setVisibility(View.INVISIBLE);
            topRightCorner.setVisibility(View.INVISIBLE);
            bottomLeftCorner.setVisibility(View.INVISIBLE);
            bottomRightCorner.setVisibility(View.INVISIBLE);
        }, 2000);
    }

    private void setupVideoView(int videoViewId, int videoResourceId) {
        VideoView videoView = findViewById(videoViewId);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResourceId);
        videoView.setVideoURI(videoUri);
        videoView.setOnCompletionListener(mp -> videoView.start());
        videoView.start();
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
