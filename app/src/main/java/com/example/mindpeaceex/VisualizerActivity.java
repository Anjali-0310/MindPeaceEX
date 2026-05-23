package com.example.mindpeaceex;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class VisualizerActivity extends AppCompatActivity {

    LinearLayout happy, sad, calm, rootLayout;
    TextView happyQuote, sadQuote, calmQuote;

    MediaPlayer mediaPlayer;
    Vibrator vibrator;

    Random random = new Random();

    String[] happyQuotes = {
            "Happiness looks good on you 😊",
            "Keep smiling, it suits you 💛",
            "Joy is your natural state ✨"
    };

    String[] sadQuotes = {
            "It's okay to feel sad 💙",
            "This too shall pass 🌙",
            "You are stronger than you think 💪"
    };

    String[] calmQuotes = {
            "Breathe in peace 🌿",
            "Slow down, you're doing fine 🍃",
            "Peace begins within 🧘"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizer);

        happy = findViewById(R.id.happy);
        sad = findViewById(R.id.sad);
        calm = findViewById(R.id.calm);
        rootLayout = findViewById(R.id.rootLayout);

        happyQuote = findViewById(R.id.happyQuote);
        sadQuote = findViewById(R.id.sadQuote);
        calmQuote = findViewById(R.id.calmQuote);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        happy.setOnClickListener(v ->
                handleMood(v, happyQuote, happyQuotes, R.raw.happy, 0xFF6A11CB, 0xFF2575FC));

        sad.setOnClickListener(v ->
                handleMood(v, sadQuote, sadQuotes, R.raw.sad, 0xFF232526, 0xFF414345));

        calm.setOnClickListener(v ->
                handleMood(v, calmQuote, calmQuotes, R.raw.calm, 0xFF11998E, 0xFF38EF7D));
    }

    private void handleMood(View card, TextView target, String[] quotes,
                            int soundRes, int colorStart, int colorEnd) {

        // RESET QUOTES
        happyQuote.setAlpha(0f);
        sadQuote.setAlpha(0f);
        calmQuote.setAlpha(0f);

        // SOUND
        if (mediaPlayer != null) mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(this, soundRes);
        mediaPlayer.start();

        // VIBRATION
        if (vibrator != null) vibrator.vibrate(100);

        // CARD ANIMATION
        card.animate().scaleX(1.05f).scaleY(1.05f).setDuration(150)
                .withEndAction(() ->
                        card.animate().scaleX(1f).scaleY(1f).setDuration(150)
                );

        // BACKGROUND ANIMATION
        animateBackground(colorStart, colorEnd);

        // TEXT ANIMATION
        target.setText(getRandom(quotes));
        target.setAlpha(0f);
        target.setTranslationY(-20f);

        target.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(400)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    private void animateBackground(int from, int to) {
        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), from, to);
        animator.setDuration(500);

        animator.addUpdateListener(animation ->
                rootLayout.setBackgroundColor((int) animation.getAnimatedValue())
        );

        animator.start();
    }

    private String getRandom(String[] arr) {
        return arr[random.nextInt(arr.length)];
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) mediaPlayer.release();
    }
}