package com.example.mindpeaceex;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PTSDActivity extends AppCompatActivity {

    int step = 0;
    MediaPlayer mp;

    String[] groundingSteps = {
            "👀 Name 5 things you can see",
            "✋ Touch 4 things you can feel",
            "👂 Listen to 3 sounds",
            "👃 Notice 2 smells",
            "❤️ Focus on 1 feeling inside"
    };

    // 🔥 AI REASSURANCE MESSAGES
    String[] reassuranceMessages = {
            "You're safe right now. Take a slow breath.",
            "This feeling will pass. You are in control.",
            "Ground yourself. You are here, not there.",
            "Breathe in slowly… and out… you're okay.",
            "You are stronger than this moment.",
            "Focus on the present. You are safe."
    };

    boolean infoExpanded = false;
    boolean symptomsExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptsd);

        Button panicBtn = findViewById(R.id.panicBtn);
        Button groundBtn = findViewById(R.id.groundBtn);

        TextView groundText = findViewById(R.id.groundText);
        TextView moodResponse = findViewById(R.id.moodResponse);
        TextView reassureText = findViewById(R.id.reassureText);

        TextView ptsdInfo = findViewById(R.id.ptsdInfo);
        TextView symptomsText = findViewById(R.id.symptomsText);

        Button anxious = findViewById(R.id.anxiousBtn);
        Button calm = findViewById(R.id.calmBtn);
        Button sad = findViewById(R.id.sadBtn);

        Button breathBtn = findViewById(R.id.breathBtn);
        Button soundBtn = findViewById(R.id.soundBtn);

        // 🧘 PANIC BUTTON (AI + SOUND)
        panicBtn.setOnClickListener(v -> {

            // release previous player (IMPORTANT FIX)
            if (mp != null) {
                mp.release();
            }

            mp = MediaPlayer.create(this, R.raw.calm);
            mp.start();

            // random AI message
            int index = (int) (Math.random() * reassuranceMessages.length);
            reassureText.setText(reassuranceMessages[index]);

            startActivity(new Intent(this, BreathingActivity.class));
        });

        // 🌿 GROUNDING STEPS
        groundBtn.setOnClickListener(v -> {
            if (step < groundingSteps.length) {
                groundText.setText(groundingSteps[step]);
                step++;
            } else {
                groundText.setText("🌿 You are grounded. You’re safe.");
                step = 0;
            }
        });

        // 😊 MOOD CHECK
        anxious.setOnClickListener(v ->
                moodResponse.setText("Take a slow breath. You’re not in danger right now."));

        calm.setOnClickListener(v ->
                moodResponse.setText("Stay with this calm feeling. You’re doing well."));

        sad.setOnClickListener(v ->
                moodResponse.setText("It’s okay to feel this way. You’re not alone."));

        // 📖 PTSD INFO (EXPAND / COLLAPSE)
        ptsdInfo.setOnClickListener(v -> {
            if (!infoExpanded) {
                ptsdInfo.setText(
                        "PTSD occurs after experiencing trauma. It can cause flashbacks, anxiety, and emotional distress. " +
                                "It affects how the brain processes fear and memory."
                );
                infoExpanded = true;
            } else {
                ptsdInfo.setText("PTSD is caused by trauma. Tap to learn more...");
                infoExpanded = false;
            }
        });

        // ⚠️ SYMPTOMS (EXPAND / COLLAPSE)
        symptomsText.setOnClickListener(v -> {
            if (!symptomsExpanded) {
                symptomsText.setText(
                        "• Flashbacks\n" +
                                "• Nightmares\n" +
                                "• Anxiety\n" +
                                "• Avoidance\n" +
                                "• Sleep problems\n" +
                                "• Emotional numbness"
                );
                symptomsExpanded = true;
            } else {
                symptomsText.setText("Tap to view symptoms");
                symptomsExpanded = false;
            }
        });

        // 🔗 QUICK TOOLS
        breathBtn.setOnClickListener(v ->
                startActivity(new Intent(this, BreathingActivity.class)));

        soundBtn.setOnClickListener(v ->
                startActivity(new Intent(this, SoundActivity.class)));
    }

    // 🔥 CLEANUP (VERY IMPORTANT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }
}