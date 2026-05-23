package com.example.mindpeaceex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MoodDashboardActivity extends AppCompatActivity {

    LinearLayout guided, journal, gratitude, breathing, visualizer, sound, reminder;

    // 🔥 NEW FEATURES
    LinearLayout calculator, camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_dashboard);

        // 🔥 LINK ALL CARDS
        guided = findViewById(R.id.guided);
        journal = findViewById(R.id.journal);
        gratitude = findViewById(R.id.gratitude);
        breathing = findViewById(R.id.breathing);
        visualizer = findViewById(R.id.visualizer);
        sound = findViewById(R.id.sound);
        reminder = findViewById(R.id.reminder);

        // 🔥 NEW CARDS (ADD THESE IDs IN XML)
        calculator = findViewById(R.id.calculatorCard);
        camera = findViewById(R.id.cameraCard);

        // 🔥 CLICK EVENTS

        guided.setOnClickListener(v ->
                startActivity(new Intent(MoodDashboardActivity.this, GuidedMeditationActivity.class)));

        journal.setOnClickListener(v ->
                startActivity(new Intent(MoodDashboardActivity.this, MoodJournalActivity.class)));

        gratitude.setOnClickListener(v ->
                startActivity(new Intent(MoodDashboardActivity.this, GratitudeActivity.class)));

        breathing.setOnClickListener(v ->
                startActivity(new Intent(MoodDashboardActivity.this, BreathingActivity.class)));

        visualizer.setOnClickListener(v ->
                startActivity(new Intent(MoodDashboardActivity.this, VisualizerActivity.class)));

        sound.setOnClickListener(v ->
                startActivity(new Intent(MoodDashboardActivity.this, SoundActivity.class)));

        reminder.setOnClickListener(v ->
                startActivity(new Intent(MoodDashboardActivity.this, ReminderActivity.class)));

        // 🔥 NEW FEATURE NAVIGATION

        calculator.setOnClickListener(v ->
                startActivity(new Intent(MoodDashboardActivity.this, CalculatorActivity.class)));

        camera.setOnClickListener(v ->
                startActivity(new Intent(MoodDashboardActivity.this, CameraActivity.class)));
    }
}