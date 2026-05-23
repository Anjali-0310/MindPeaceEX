package com.example.mindpeaceex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    ImageButton nextBtn, backBtn;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        backBtn = findViewById(R.id.backBtn);
        signupBtn = findViewById(R.id.signupBtn);

        // BACK
        backBtn.setOnClickListener(v -> finish());

        // SIGN UP BUTTON (MAIN ACTION)
        signupBtn.setOnClickListener(v -> {
            startActivity(new Intent(AboutActivity.this, SignUpActivity.class));
        });
    }
}