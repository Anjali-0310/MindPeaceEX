package com.example.mindpeaceex;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GratitudeActivity extends AppCompatActivity {

    Spinner templateSpinner;
    EditText input;
    Button saveBtn;
    LinearLayout entryContainer;

    String selectedMood = "😄";

    AppDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gratitude);

        templateSpinner = findViewById(R.id.templateSpinner);
        input = findViewById(R.id.input);
        saveBtn = findViewById(R.id.saveBtn);
        entryContainer = findViewById(R.id.entryContainer);

        db = new AppDBHelper(this);

        // Templates
        String[] templates = {
                "Daily Reflection",
                "3 Things Gratitude",
                "People Gratitude",
                "Mindfulness"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, templates);
        templateSpinner.setAdapter(adapter);

        findViewById(R.id.mood1).setOnClickListener(v -> selectedMood = "😄");
        findViewById(R.id.mood2).setOnClickListener(v -> selectedMood = "😢");
        findViewById(R.id.mood3).setOnClickListener(v -> selectedMood = "😌");

        saveBtn.setOnClickListener(v -> saveEntry());

        // LOAD SAVED DATA
        loadEntries();
    }

    private void saveEntry() {

        String text = input.getText().toString().trim();

        if (text.isEmpty()) {
            Toast.makeText(this, "Write something", Toast.LENGTH_SHORT).show();
            return;
        }

        String template = templateSpinner.getSelectedItem().toString();

        String time = new SimpleDateFormat("hh:mm a", Locale.getDefault())
                .format(new Date());

        // SAVE
        db.insertGratitude(selectedMood, template, text);

        input.setText("");
        Toast.makeText(this, "Saved 💛", Toast.LENGTH_SHORT).show();

        loadEntries(); // refresh
    }

    // ✅ LOAD FROM DB
    private void loadEntries() {

        entryContainer.removeAllViews();

        Cursor cursor = db.getAllGratitudeData();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String mood = cursor.getString(1);
                String template = cursor.getString(2);
                String text = cursor.getString(3);

                createCard(id, mood, template, text);

            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    // ✅ CREATE CARD WITH EDIT + DELETE
    private void createCard(int id, String mood, String template, String text) {

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

        TextView header = new TextView(this);
        header.setText(mood + "   " + template);
        header.setTextColor(getColor(android.R.color.white));
        header.setAlpha(0.8f);
        header.setTextSize(14);

        TextView content = new TextView(this);
        content.setText(text);
        content.setTextColor(getColor(android.R.color.white));
        content.setTextSize(16);

        // BUTTON LAYOUT
        LinearLayout btnLayout = new LinearLayout(this);
        btnLayout.setOrientation(LinearLayout.HORIZONTAL);

        Button editBtn = new Button(this);
        editBtn.setText("Edit");

        Button deleteBtn = new Button(this);
        deleteBtn.setText("Delete");

        // DELETE
        deleteBtn.setOnClickListener(v -> {
            db.deleteGratitude(id);
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            loadEntries();
        });

        // EDIT
        editBtn.setOnClickListener(v -> {

            EditText edit = new EditText(this);
            edit.setText(text);

            new android.app.AlertDialog.Builder(this)
                    .setTitle("Edit Gratitude")
                    .setView(edit)
                    .setPositiveButton("Update", (dialog, which) -> {
                        String newText = edit.getText().toString();
                        db.updateGratitude(id, newText);
                        loadEntries();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        btnLayout.addView(editBtn);
        btnLayout.addView(deleteBtn);

        card.addView(header);
        card.addView(content);
        card.addView(btnLayout);

        entryContainer.addView(card);
    }
}