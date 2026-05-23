package com.example.mindpeaceex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    ImageButton nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        nextBtn = findViewById(R.id.nextBtn);

        nextBtn.setOnClickListener(v -> {
            startActivity(new Intent(SplashActivity.this, AboutActivity.class));
            finish();
        });
    }
}