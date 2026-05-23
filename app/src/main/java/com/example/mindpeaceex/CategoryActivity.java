package com.example.mindpeaceex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {

    LinearLayout sleep, focus, calm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        sleep = findViewById(R.id.sleepCard);
        focus = findViewById(R.id.focusCard);
        calm = findViewById(R.id.calmCard);

        sleep.setOnClickListener(v -> openBreathing("sleep"));
        focus.setOnClickListener(v -> openBreathing("focus"));
        calm.setOnClickListener(v -> openBreathing("calm"));
    }

    private void openBreathing(String type) {
        Intent intent = new Intent(this, BreathingActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}