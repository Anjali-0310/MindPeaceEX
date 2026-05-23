package com.example.mindpeaceex;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MoodJournalActivity extends AppCompatActivity {

    EditText journalInput;
    Button saveBtn;
    LinearLayout entryContainer;

    TextView moodHappy, moodSad, moodCalm;

    String selectedMood = "😐";

    // ✅ DATABASE
    AppDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_journal);

        journalInput = findViewById(R.id.journalInput);
        saveBtn = findViewById(R.id.saveBtn);
        entryContainer = findViewById(R.id.entryContainer);

        moodHappy = findViewById(R.id.moodHappy);
        moodSad = findViewById(R.id.moodSad);
        moodCalm = findViewById(R.id.moodCalm);

        db = new AppDBHelper(this);

        // MOOD SELECT
        moodHappy.setOnClickListener(v -> selectMood("😄", v));
        moodSad.setOnClickListener(v -> selectMood("😢", v));
        moodCalm.setOnClickListener(v -> selectMood("😌", v));

        // SAVE ENTRY
        saveBtn.setOnClickListener(v -> {

            String text = journalInput.getText().toString().trim();

            if (text.isEmpty()) {
                Toast.makeText(this, "Write something first", Toast.LENGTH_SHORT).show();
                return;
            }

            addEntry(text);
            journalInput.setText("");
        });

        // LOAD DATA
        loadEntries();
    }

    private void selectMood(String mood, View view) {
        selectedMood = mood;

        moodHappy.setAlpha(0.4f);
        moodSad.setAlpha(0.4f);
        moodCalm.setAlpha(0.4f);

        view.setAlpha(1f);
    }

    private void addEntry(String text) {

        String time = new SimpleDateFormat("hh:mm a", Locale.getDefault())
                .format(new Date());

        // ✅ SAVE
        db.insertJournal(selectedMood, text, time);

        // 🔥 REFRESH
        loadEntries();
    }

    // ✅ UPDATED (NOW USES DB WITH ID)
    private void loadEntries() {

        entryContainer.removeAllViews();

        Cursor cursor = db.getAllJournalData();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String mood = cursor.getString(1);
                String text = cursor.getString(2);
                String time = cursor.getString(3);

                createCard(id, mood, text, time);

            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    // ✅ UPDATED (NOW HAS DELETE + EDIT)
    private void createCard(int id, String mood, String text, String time) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(20, 20, 20, 20);
        card.setBackground(getDrawable(R.drawable.card_bg));

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 15, 0, 0);
        card.setLayoutParams(params);

        TextView moodTime = new TextView(this);
        moodTime.setText(mood + "   " + time);
        moodTime.setTextColor(getColor(android.R.color.white));
        moodTime.setAlpha(0.8f); // slightly faded time
        moodTime.setTextSize(14);

        TextView content = new TextView(this);
        content.setText(text);
        content.setTextColor(getColor(android.R.color.white));
        moodTime.setAlpha(0.8f); // slightly faded time
        content.setTextSize(16);

        // 🔹 BUTTON LAYOUT
        LinearLayout btnLayout = new LinearLayout(this);
        btnLayout.setOrientation(LinearLayout.HORIZONTAL);

        Button editBtn = new Button(this);
        editBtn.setText("Edit");

        Button deleteBtn = new Button(this);
        deleteBtn.setText("Delete");

        // 🔥 DELETE
        deleteBtn.setOnClickListener(v -> {
            db.deleteJournal(id);
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            loadEntries();
        });

        // 🔥 UPDATE
        editBtn.setOnClickListener(v -> {

            EditText edit = new EditText(this);
            edit.setText(text);

            new android.app.AlertDialog.Builder(this)
                    .setTitle("Edit Entry")
                    .setView(edit)
                    .setPositiveButton("Update", (dialog, which) -> {
                        String newText = edit.getText().toString();
                        db.updateJournal(id, newText);
                        loadEntries();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        btnLayout.addView(editBtn);
        btnLayout.addView(deleteBtn);

        card.addView(moodTime);
        card.addView(content);
        card.addView(btnLayout);

        entryContainer.addView(card);
    }
}