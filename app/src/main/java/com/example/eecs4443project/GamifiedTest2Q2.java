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
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

public class GamifiedTest2Q2 extends GamifiedTest2Q1 {
    private final List<Integer> userSelections = new ArrayList<>();
    private final int[] correctOrder = { R.id.videoView1, R.id.videoView2, R.id.videoView3 };
    private final static String MYDEBUG = "MYDEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MYDEBUG, "Got here!");
        setContentView(R.layout.gamified_test2_q2);

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        if (Timer.isTimerRunning(this)) {
            timerRunnable.run();
        }

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamifiedTest2Q2.this, EndGamifiedLesson.class);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamifiedTest2Q2.this, GamifiedTest2Q1.class);
                startActivity(intent);
            }
        });

//        VideoView videoView = findViewById(R.id.videoView);
//
//        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.l2q1;
//        Uri uri = Uri.parse(videoPath);
//        videoView.setVideoURI(uri);
//
//        videoView.start();
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.setLooping(true);
//                videoView.start();
//            }
//        });

        VideoView videoView1 = findViewById(R.id.videoView1);
        VideoView videoView2 = findViewById(R.id.videoView2);
        VideoView videoView3 = findViewById(R.id.videoView3);

        videoView1.setFocusable(false);
        videoView2.setFocusable(false);
        videoView3.setFocusable(false);

        videoView1.setOnClickListener(this::onChoiceClick);
        videoView2.setOnClickListener(this::onChoiceClick);
        videoView3.setOnClickListener(this::onChoiceClick);

        setupVideoView(R.id.videoView1, R.raw.l2q2a1);
        setupVideoView(R.id.videoView2, R.raw.l2q2a2);
        setupVideoView(R.id.videoView3, R.raw.l2q2a3);

        if (correctOrder[0] == R.id.videoView1) {
            videoView1.setTag("incorrect");
        } else {
            videoView1.setTag(null);
        }

        if (correctOrder[1] == R.id.videoView2) {
            videoView2.setTag("correct");
        } else {
            videoView2.setTag(null);
        }

        if (correctOrder[2] == R.id.videoView3) {
            videoView3.setTag("incorrect");
        } else {
            videoView3.setTag(null);
        }
    }

    private void setupVideoView(int videoViewId, int videoResourceId) {
        VideoView videoView = findViewById(videoViewId);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResourceId);
        videoView.setVideoURI(videoUri);
        videoView.setOnCompletionListener(mp -> videoView.start());
        videoView.start();
    }

    public void onChoiceClick(View view) {
        view.setEnabled(false);
        boolean isAnswerCorrect = view.getTag() != null && view.getTag().equals("correct");
        userSelections.add(view.getId());
        if (userSelections.size() == correctOrder.length) {
            checkSequence();
        }
    }

    private void checkSequence() {
        int topLeftDrawable, topRightDrawable, bottomLeftDrawable, bottomRightDrawable;

        boolean isCorrect = true;
        for (int i = 0; i < correctOrder.length; i++) {
            if (!userSelections.get(i).equals(correctOrder[i])) {
                isCorrect = false;
                break;
            }
        }

        if (isCorrect) {
            topLeftDrawable = R.drawable.green_top_left_corner;
            topRightDrawable = R.drawable.green_top_right_corner;
            bottomLeftDrawable = R.drawable.green_bottom_left_corner;
            bottomRightDrawable = R.drawable.green_bottom_right_corner;
        } else {
            topLeftDrawable = R.drawable.red_top_left_corner;
            topRightDrawable = R.drawable.red_top_right_corner;
            bottomLeftDrawable = R.drawable.red_bottom_left_corner;
            bottomRightDrawable = R.drawable.red_bottom_right_corner;

            Counter.incrementWrongAnswerCount(this, "Question 2");
            int wrongAnswerCounter = Counter.getWrongAnswerCount(this, "Question 2");
            Log.i(MYDEBUG, "You've answered Test 2 Question 2 incorrectly " + wrongAnswerCounter + " times.");

            View rootView = findViewById(android.R.id.content);
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            rootView.startAnimation(shake);

            resetSelections();
            userSelections.clear();
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

        new Handler().postDelayed(() -> {
            topLeftCorner.setVisibility(View.INVISIBLE);
            topRightCorner.setVisibility(View.INVISIBLE);
            bottomLeftCorner.setVisibility(View.INVISIBLE);
            bottomRightCorner.setVisibility(View.INVISIBLE);
        }, 2000); // after 2 seconds, for example
    }

    private void resetSelections() {
        userSelections.clear();
        resetVideoView(R.id.videoView1);
        resetVideoView(R.id.videoView2);
        resetVideoView(R.id.videoView3);
    }

    private void resetVideoView(int videoViewId) {
        VideoView videoView = findViewById(videoViewId);
        videoView.setEnabled(true);
        videoView.setTag(videoViewId == correctOrder[0] ? "correct" : null);
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
