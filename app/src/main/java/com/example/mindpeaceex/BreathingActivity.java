package com.example.mindpeaceex;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BreathingActivity extends AppCompatActivity {

    TextView breathText, sessionTimer;
    Button startBtn, stopBtn;
    ProgressBar progressRing;
    android.view.View circle;

    boolean running = false;

    int inhaleTime = 4000;
    int exhaleTime = 4000;

    int totalSession = 60000;
    int timeLeft = totalSession;

    Handler handler = new Handler();
    CountDownTimer sessionTimerObj;

    MediaPlayer sound;
    Vibrator vibrator;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathing);

        circle = findViewById(R.id.breathCircle);
        breathText = findViewById(R.id.breathText);
        startBtn = findViewById(R.id.startBtn);
        stopBtn = findViewById(R.id.stopBtn);
        sessionTimer = findViewById(R.id.sessionTimer);
        progressRing = findViewById(R.id.progressRing);

        sound = MediaPlayer.create(this, R.raw.breath);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        type = getIntent().getStringExtra("type");

        if (type != null) {
            switch (type) {
                case "sleep":
                    inhaleTime = 6000;
                    exhaleTime = 6000;
                    totalSession = 120000;
                    break;
                case "focus":
                    inhaleTime = 3000;
                    exhaleTime = 3000;
                    totalSession = 60000;
                    break;
                case "calm":
                    inhaleTime = 4000;
                    exhaleTime = 4000;
                    totalSession = 90000;
                    break;
            }
        }

        timeLeft = totalSession;
        updateTimerText();

        startBtn.setOnClickListener(v -> {
            if (!running) {
                running = true;
                startSessionTimer();
                inhale();
            }
        });

        stopBtn.setOnClickListener(v -> stopSession());
    }

    // ✅ SESSION TIMER
    private void startSessionTimer() {
        sessionTimerObj = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = (int) millisUntilFinished;

                int progress = (int) ((totalSession - timeLeft) * 100 / totalSession);
                progressRing.setProgress(progress);

                updateTimerText();
            }

            @Override
            public void onFinish() {
                stopSession();
                breathText.setText("Done 🧘");
            }
        }.start();
    }

    // ✅ INHALE (SYNC FIXED)
    private void inhale() {
        if (!running) return;

        breathText.setText("Inhale");

        animate(1f, 1.8f, inhaleTime);

        handler.postDelayed(this::playFeedback, 200); // 🔥 sync

        handler.postDelayed(this::exhale, inhaleTime);
    }

    // ✅ EXHALE (SYNC FIXED)
    private void exhale() {
        if (!running) return;

        breathText.setText("Exhale");

        animate(1.8f, 1f, exhaleTime);

        handler.postDelayed(this::playFeedback, 200); // 🔥 sync

        handler.postDelayed(this::inhale, exhaleTime);
    }

    private void animate(float from, float to, int duration) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(circle, "scaleX", from, to);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(circle, "scaleY", from, to);

        scaleX.setDuration(duration);
        scaleY.setDuration(duration);

        scaleX.start();
        scaleY.start();
    }

    private void playFeedback() {
        try {
            if (sound != null) {
                sound.seekTo(0);
                sound.start();
            }
        } catch (Exception ignored) {}

        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(100);
            }
        }
    }

    private void updateTimerText() {
        int minutes = timeLeft / 1000 / 60;
        int seconds = (timeLeft / 1000) % 60;

        sessionTimer.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void stopSession() {
        running = false;

        if (sessionTimerObj != null) sessionTimerObj.cancel();
        handler.removeCallbacksAndMessages(null);

        if (sound != null && sound.isPlaying()) {
            sound.pause();
            sound.seekTo(0);
        }

        timeLeft = totalSession;
        progressRing.setProgress(0);
        updateTimerText();

        breathText.setText("Ready");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (sound != null) {
            sound.release();
        }
    }
}