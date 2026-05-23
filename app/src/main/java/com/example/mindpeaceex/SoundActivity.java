package com.example.mindpeaceex;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class SoundActivity extends AppCompatActivity {

    HashMap<Integer, MediaPlayer> players = new HashMap<>();
    HashMap<Integer, Boolean> isPlaying = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        setupSound(R.id.rain, R.raw.rain);
        setupSound(R.id.ocean, R.raw.ocean);
        setupSound(R.id.forest, R.raw.forest);
        setupSound(R.id.fire, R.raw.fire);
        setupSound(R.id.piano, R.raw.piano);
        setupSound(R.id.night, R.raw.night);

        Button stopAll = findViewById(R.id.stopAll);
        stopAll.setOnClickListener(v -> stopAllSounds());
    }

    private void setupSound(int viewId, int soundRes) {
        LinearLayout card = findViewById(viewId);

        MediaPlayer player = MediaPlayer.create(this, soundRes);
        player.setLooping(true);

        players.put(viewId, player);
        isPlaying.put(viewId, false);

        card.setOnClickListener(v -> {
            boolean playing = isPlaying.get(viewId);

            if (!playing) {
                player.start();
                card.setAlpha(0.6f); // visual active
                isPlaying.put(viewId, true);
            } else {
                player.pause();
                card.setAlpha(1f);
                isPlaying.put(viewId, false);
            }
        });
    }

    private void stopAllSounds() {
        for (int key : players.keySet()) {
            MediaPlayer p = players.get(key);
            if (p != null && p.isPlaying()) {
                p.pause();
                p.seekTo(0);
            }

            LinearLayout card = findViewById(key);
            card.setAlpha(1f);

            isPlaying.put(key, false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        for (MediaPlayer p : players.values()) {
            if (p != null) {
                p.release();
            }
        }
    }
}