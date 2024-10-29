package com.example.eecs4443project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.core.content.ContextCompat;

public class GamifiedTest1Q1 extends startTest1GamifiedSure{
    private static final String MYDEBUG = "MYDEBUG";
    long startTime = 0;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (!Timer.isTimerRunning(GamifiedTest1Q1.this)) {
                timerHandler.removeCallbacks(this);
                return;
            }

            long millis = System.currentTimeMillis() - Timer.getStartTime(GamifiedTest1Q1.this);
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
        setContentView(R.layout.gamified_test1_q1);

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        if (Timer.isTimerRunning(this)) {
            timerRunnable.run();
        }

        ImageView imageView6 = findViewById(R.id.imageView6);
        ImageView imageView8 = findViewById(R.id.imageView8);
        ImageView imageView9 = findViewById(R.id.imageView9);
        ImageView imageView10 = findViewById(R.id.imageView10);

        imageView6.setOnClickListener(this::onChoiceClick);
        imageView8.setOnClickListener(this::onChoiceClick);
        imageView9.setOnClickListener(this::onChoiceClick);
        imageView10.setOnClickListener(this::onChoiceClick);

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamifiedTest1Q1.this, GamifiedTest1Q2.class);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamifiedTest1Q1.this, startTest1Gamified.class);
                startActivity(intent);
            }
        });

        VideoView videoView = findViewById(R.id.videoView);

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.l1q1;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
                videoView.start();
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
            Counter.incrementWrongAnswerCount(this, "Question 1");
            int wrongAnswerCounter = Counter.getWrongAnswerCount(this, "Question 1");
            Log.i(MYDEBUG, "You've answered Gamfiied Test 1 Question 1 incorrectly " + wrongAnswerCounter + " times.");

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

    // Possibly a Vibrate method that could be of help
//            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//            if (vibrator != null) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
//                } else {
//                    vibrator.vibrate(500);
//                }
//            }
}
