package com.example.mindpeaceex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.core.view.GravityCompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    LinearLayout moodHappy, moodCalm, moodRelax, moodFocus, moodCard, ptsdCard;
    Button logoutBtn;
    BottomNavigationView bottomNav;

    // 🔥 ADDED FOR MENU (ONLY REQUIRED)
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // INIT
        moodHappy = findViewById(R.id.moodHappy);
        moodCalm = findViewById(R.id.moodCalm);
        moodRelax = findViewById(R.id.moodRelax);
        moodFocus = findViewById(R.id.moodFocus);
        moodCard = findViewById(R.id.moodCard);
        ptsdCard = findViewById(R.id.ptsdCard);

        logoutBtn = findViewById(R.id.logoutBtn);
        bottomNav = findViewById(R.id.bottomNav);

        // 🔥 MENU INIT (NEW)
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        menuIcon = findViewById(R.id.menuIcon);

        // 🔥 OPEN DRAWER
        menuIcon.setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START)
        );

        // 🔥 MENU CLICK EVENTS
        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // already here
            }
            else if (id == R.id.nav_journal) {
                startActivity(new Intent(this, MoodJournalActivity.class));
            }
            else if (id == R.id.nav_gratitude) {
                startActivity(new Intent(this, GratitudeActivity.class));
            }
            else if (id == R.id.nav_logout) {
                logout();
            }

            drawerLayout.closeDrawers();
            return true;
        });

        // ✅ LOGOUT (UNCHANGED)
        logoutBtn.setOnClickListener(v -> logout());

        // NAVIGATION
        moodCard.setOnClickListener(v -> {
            clickAnim(moodCard);
            startActivity(new Intent(this, MoodDashboardActivity.class));
        });

        ptsdCard.setOnClickListener(v -> {
            clickAnim(ptsdCard);
            startActivity(new Intent(this, PTSDActivity.class));
        });

        moodHappy.setOnClickListener(v -> startActivity(new Intent(this, MoodDashboardActivity.class)));
        moodCalm.setOnClickListener(v -> startActivity(new Intent(this, MoodDashboardActivity.class)));
        moodRelax.setOnClickListener(v -> startActivity(new Intent(this, MoodDashboardActivity.class)));
        moodFocus.setOnClickListener(v -> startActivity(new Intent(this, MoodDashboardActivity.class)));

        // 🔥 ANIMATIONS
        animateCard(moodCard);
        animateCard(ptsdCard);

        // BOTTOM NAV
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_home) return true;

            else if (item.getItemId() == R.id.nav_games) {
                startActivity(new Intent(this, FocusGameActivity.class));
                return true;
            }

            return false;
        });

        // ✅ NOTIFICATION (UNCHANGED)
        new android.os.Handler().postDelayed(() -> {
            NotificationHelper.showNotification(
                    this,
                    "Daily Reminder 🧠",
                    "How are you feeling today? Write your journal ✍️"
            );
        }, 1000);
    }

    // 🔥 LOGOUT METHOD (REUSED)
    private void logout() {

        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        prefs.edit().clear().apply();

        FirebaseAuth.getInstance().signOut();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    // 🔥 PULSE ANIMATION
    private void animateCard(LinearLayout card) {
        card.animate()
                .scaleX(1.03f)
                .scaleY(1.03f)
                .setDuration(1200)
                .withEndAction(() ->
                        card.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(1200)
                                .withEndAction(() -> animateCard(card))
                );
    }

    // 🔥 CLICK EFFECT
    private void clickAnim(LinearLayout card) {
        card.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100)
                .withEndAction(() ->
                        card.animate().scaleX(1f).scaleY(1f).setDuration(100)
                );
    }

    // SESSION CHECK
    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}