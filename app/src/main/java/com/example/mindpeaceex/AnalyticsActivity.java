package com.example.mindpeaceex;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public class AnalyticsActivity extends AppCompatActivity {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        result = findViewById(R.id.result);

        SharedPreferences prefs = getSharedPreferences("data", MODE_PRIVATE);
        Set<String> entries = prefs.getStringSet("entries", null);

        if (entries == null || entries.isEmpty()) {
            result.setText("No data yet");
            return;
        }

        int happy = 0, sad = 0, calm = 0;

        for (String e : entries) {
            if (e.contains("😄")) happy++;
            else if (e.contains("😢")) sad++;
            else if (e.contains("😌")) calm++;
        }

        String insights =
                "😊 Happy: " + happy +
                        "\n😢 Sad: " + sad +
                        "\n😌 Calm: " + calm +
                        "\n\n✨ Keep journaling to improve mood!";

        result.setText(insights);
    }
}