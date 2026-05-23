package com.example.mindpeaceex;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class CalculatorActivity extends AppCompatActivity {

    EditText input1, input2;
    TextView result, operatorView;

    Button addBtn, subBtn, mulBtn, divBtn, calcBtn;

    String operator = "+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        result = findViewById(R.id.result);
        operatorView = findViewById(R.id.operatorView);

        addBtn = findViewById(R.id.addBtn);
        subBtn = findViewById(R.id.subBtn);
        mulBtn = findViewById(R.id.mulBtn);
        divBtn = findViewById(R.id.divBtn);
        calcBtn = findViewById(R.id.calcBtn);

        // 🔥 Operator selection (visual + logic)
        addBtn.setOnClickListener(v -> {
            operator = "+";
            operatorView.setText("+");
        });

        subBtn.setOnClickListener(v -> {
            operator = "-";
            operatorView.setText("-");
        });

        mulBtn.setOnClickListener(v -> {
            operator = "*";
            operatorView.setText("×");
        });

        divBtn.setOnClickListener(v -> {
            operator = "/";
            operatorView.setText("÷");
        });

        calcBtn.setOnClickListener(v -> calculate());
    }

    private void calculate() {

        try {
            String val1 = input1.getText().toString();
            String val2 = input2.getText().toString();

            if (val1.isEmpty() || val2.isEmpty()) {
                result.setText("Enter both values");
                return;
            }

            double a = Double.parseDouble(val1);
            double b = Double.parseDouble(val2);

            double res = 0;

            switch (operator) {
                case "+":
                    res = a + b;
                    break;
                case "-":
                    res = a - b;
                    break;
                case "*":
                    res = a * b;
                    break;
                case "/":
                    if (b == 0) {
                        result.setText("Cannot divide by zero");
                        return;
                    }
                    res = a / b;
                    break;
            }

            // 🧠 INTERPRETATION
            String message;

            if (res <= 5) {
                message = "🌿 Low Stress — You're calm";
            } else if (res <= 10) {
                message = "⚡ Moderate — Take a break";
            } else {
                message = "🔥 High Stress — Try breathing exercise";
            }

            result.setText("Score: " + res + "\n" + message);

        } catch (Exception e) {
            result.setText("Invalid Input");
        }
    }
}