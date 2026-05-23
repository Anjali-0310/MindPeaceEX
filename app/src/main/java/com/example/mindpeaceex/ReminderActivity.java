package com.example.mindpeaceex;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.*;

public class ReminderActivity extends AppCompatActivity {

    EditText reminderInput;
    Button setBtn, timeBtn;
    TextView timeText;
    CheckBox repeatCheck;
    LinearLayout reminderList;

    Calendar selectedTime = Calendar.getInstance();
    float downX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        reminderInput = findViewById(R.id.reminderInput);
        setBtn = findViewById(R.id.setBtn);
        timeBtn = findViewById(R.id.timeBtn);
        timeText = findViewById(R.id.timeText);
        repeatCheck = findViewById(R.id.repeatCheck);
        reminderList = findViewById(R.id.reminderList);

        timeBtn.setOnClickListener(v -> pickTime());
        setBtn.setOnClickListener(v -> saveReminder());

        loadReminders();
    }

    private void pickTime() {
        TimePickerDialog picker = new TimePickerDialog(this,
                (view, hour, minute) -> {
                    selectedTime.set(Calendar.HOUR_OF_DAY, hour);
                    selectedTime.set(Calendar.MINUTE, minute);
                    selectedTime.set(Calendar.SECOND, 0);

                    String time = new SimpleDateFormat("hh:mm a", Locale.getDefault())
                            .format(selectedTime.getTime());

                    timeText.setText(time);
                },
                selectedTime.get(Calendar.HOUR_OF_DAY),
                selectedTime.get(Calendar.MINUTE),
                false);

        picker.show();
    }

    private void saveReminder() {

        String text = reminderInput.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this, "Enter reminder", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean repeat = repeatCheck.isChecked();
        int id = (int) System.currentTimeMillis();

        scheduleReminder(text, id, repeat);
        saveToStorage(text, id, repeat);

        reminderInput.setText("");
        loadReminders();

        Toast.makeText(this, "Reminder Set ✅", Toast.LENGTH_SHORT).show();
    }

    private void scheduleReminder(String text, int id, boolean repeat) {

        if (selectedTime.before(Calendar.getInstance())) {
            selectedTime.add(Calendar.DAY_OF_MONTH, 1);
        }

        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.putExtra("msg", text);
        intent.putExtra("id", id);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (repeat) {
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    selectedTime.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        } else {

            // 🔥 ANDROID 12+ FIX
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {

                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            selectedTime.getTimeInMillis(),
                            pendingIntent
                    );
                } else {
                    Toast.makeText(this, "Allow exact alarm permission!", Toast.LENGTH_LONG).show();
                }

            } else {
                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        selectedTime.getTimeInMillis(),
                        pendingIntent
                );
            }
        }
    }

    private void saveToStorage(String text, int id, boolean repeat) {

        SharedPreferences prefs = getSharedPreferences("reminders", MODE_PRIVATE);
        Set<String> set = prefs.getStringSet("list", new HashSet<>());

        Set<String> newSet = new HashSet<>(set);
        newSet.add(id + "|" + text + "|" + repeat);

        prefs.edit().putStringSet("list", newSet).apply();
    }

    private void loadReminders() {

        reminderList.removeAllViews();

        SharedPreferences prefs = getSharedPreferences("reminders", MODE_PRIVATE);
        Set<String> set = prefs.getStringSet("list", null);

        if (set == null) return;

        for (String r : set) {

            String[] parts = r.split("\\|");
            int id = Integer.parseInt(parts[0]);
            String text = parts[1];

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.HORIZONTAL);
            card.setPadding(30, 30, 30, 30);
            card.setBackground(getDrawable(R.drawable.glass_card));
            card.setElevation(5f);

            TextView tv = new TextView(this);
            tv.setText(text);
            tv.setTextColor(getColor(android.R.color.white));
            tv.setLayoutParams(new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));

            card.addView(tv);

            // 🔥 SWIPE DELETE
            card.setOnTouchListener((v, event) -> {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        return true;

                    case MotionEvent.ACTION_UP:
                        float upX = event.getX();

                        if (downX - upX > 150) {
                            deleteReminder(id, r);
                            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                }
                return false;
            });

            card.setAlpha(0f);
            card.animate().alpha(1f).setDuration(500);

            reminderList.addView(card);
        }
    }

    private void deleteReminder(int id, String entry) {

        Intent intent = new Intent(this, ReminderReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, id, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        SharedPreferences prefs = getSharedPreferences("reminders", MODE_PRIVATE);
        Set<String> set = prefs.getStringSet("list", new HashSet<>());

        Set<String> newSet = new HashSet<>(set);
        newSet.remove(entry);

        prefs.edit().putStringSet("list", newSet).apply();

        loadReminders();
    }
}