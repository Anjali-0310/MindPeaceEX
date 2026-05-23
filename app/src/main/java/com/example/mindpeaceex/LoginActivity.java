package com.example.mindpeaceex;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.*;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button loginButton;
    private TextView signupRedirectText;
    private FirebaseAuth auth;

    // ✅ SESSION STORAGE
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signUpRedirectText);

        auth = FirebaseAuth.getInstance();

        // ✅ INIT PREFS
        prefs = getSharedPreferences("user_session", MODE_PRIVATE);

        // ✅ AUTO LOGIN (REMEMBER USER)
        if (prefs.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

        // ✅ REQUEST NOTIFICATION PERMISSION (Android 13+)
        if (Build.VERSION.SDK_INT >= 33) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        // ✅ LOGIN
        loginButton.setOnClickListener(v -> {

            String email = loginEmail.getText().toString().trim();
            String pass = loginPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                loginEmail.setError("Enter email");
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                loginEmail.setError("Invalid email");
                return;
            }

            if (TextUtils.isEmpty(pass)) {
                loginPassword.setError("Enter password");
                return;
            }

            auth.signInWithEmailAndPassword(email, pass)
                    .addOnSuccessListener(authResult -> {

                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                        // ✅ SAVE SESSION (REMEMBER USER)
                        prefs.edit().putBoolean("isLoggedIn", true).apply();

                        // ✅ SHOW NOTIFICATION
                        NotificationHelper.showNotification(
                                this,
                                "Welcome Back 😊",
                                "How are you feeling today? Write your journal ✍️"
                        );

                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Login Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        });

        // ✅ GO TO SIGNUP
        signupRedirectText.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class))
        );
    }
}