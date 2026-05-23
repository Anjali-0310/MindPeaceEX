package com.example.mindpeaceex;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class FocusGameActivity extends AppCompatActivity {

    View target;
    TextView scoreText, timerText, comboText, particleText;

    LinearLayout resultLayout;
    TextView finalScore, rewardText;
    Button restartBtn;

    int score = 0;
    int timeLeft = 30;
    int combo = 0;

    Handler handler = new Handler();
    Random random = new Random();

    MediaPlayer tapSound;

    int screenWidth, screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_game);

        target = findViewById(R.id.target);
        scoreText = findViewById(R.id.scoreText);
        timerText = findViewById(R.id.timerText);
        comboText = findViewById(R.id.comboText);
        particleText = findViewById(R.id.particleText);

        resultLayout = findViewById(R.id.resultLayout);
        finalScore = findViewById(R.id.finalScore);
        rewardText = findViewById(R.id.rewardText);
        restartBtn = findViewById(R.id.restartBtn);

        tapSound = MediaPlayer.create(this, R.raw.tap);

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        startGame();
        pulseTarget();

        target.setOnClickListener(v -> {

            if (tapSound != null) tapSound.start();

            combo++;
            int points = 1 + (combo / 5); // 🔥 combo bonus
            score += points;

            scoreText.setText("Score: " + score);
            comboText.setText("Combo: " + combo);

            showParticle("+" + points);

            moveTarget();
        });

        restartBtn.setOnClickListener(v -> recreate());
    }

    private void startGame() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (timeLeft > 0) {
                    timeLeft--;
                    timerText.setText("Time: " + timeLeft);

                    // ❌ combo break if idle
                    if (combo > 0) combo--;

                    handler.postDelayed(this, 1000);
                } else {
                    endGame();
                }
            }
        }, 1000);
    }

    private void endGame() {

        target.setVisibility(View.GONE);
        resultLayout.setVisibility(View.VISIBLE);

        finalScore.setText("Score: " + score);

        if (score >= 40) {
            rewardText.setText("🔥 Elite Focus Master!");
        } else if (score >= 25) {
            rewardText.setText("💪 Strong Focus!");
        } else if (score >= 15) {
            rewardText.setText("👍 Good Job!");
        } else {
            rewardText.setText("🌱 Keep Practicing!");
        }
    }

    private void moveTarget() {

        int maxX = screenWidth - target.getWidth();
        int maxY = screenHeight - target.getHeight() - 300;

        int x = random.nextInt(Math.max(maxX, 1));
        int y = random.nextInt(Math.max(maxY, 1));

        target.animate()
                .x(x)
                .y(y)
                .setDuration(400)
                .setInterpolator(new OvershootInterpolator())
                .start();
    }

    // ✨ PARTICLE EFFECT
    private void showParticle(String text) {

        particleText.setText(text);
        particleText.setVisibility(View.VISIBLE);
        particleText.setAlpha(1f);

        particleText.animate()
                .translationYBy(-150)
                .alpha(0f)
                .setDuration(800)
                .withEndAction(() -> {
                    particleText.setTranslationY(0);
                    particleText.setVisibility(View.GONE);
                });
    }

    private void pulseTarget() {
        target.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(500)
                .withEndAction(() ->
                        target.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(500)
                                .withEndAction(this::pulseTarget)
                );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (tapSound != null) tapSound.release();
    }
}