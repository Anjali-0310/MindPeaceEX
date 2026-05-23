package com.example.mindpeaceex;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class GuidedMeditationActivity extends AppCompatActivity {

    Button playBtn, pauseBtn, stopBtn;
    TextView timerText;

    MediaPlayer mediaPlayer;
    CountDownTimer timer;

    int totalTime = 300000;
    int timeLeft = totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guided_meditation);

        playBtn = findViewById(R.id.playBtn);
        pauseBtn = findViewById(R.id.pauseBtn);
        stopBtn = findViewById(R.id.stopBtn);
        timerText = findViewById(R.id.timerText);

        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.meditation);

            if (mediaPlayer == null) {
                Toast.makeText(this, "Audio file missing!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error loading audio", Toast.LENGTH_LONG).show();
        }

        updateTimerText();

        playBtn.setOnClickListener(v -> {
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                startTimer();
            } else {
                Toast.makeText(this, "Audio not available", Toast.LENGTH_SHORT).show();
            }
        });

        pauseBtn.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                if (timer != null) timer.cancel();
            }
        });

        stopBtn.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            if (timer != null) timer.cancel();

            timeLeft = totalTime;
            updateTimerText();
        });
    }

    private void startTimer() {
        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = (int) millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                timerText.setText("Done 🧘");
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
            }
        }.start();
    }

    private void updateTimerText() {
        int minutes = timeLeft / 1000 / 60;
        int seconds = (timeLeft / 1000) % 60;

        String time = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerText.setText(time);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}