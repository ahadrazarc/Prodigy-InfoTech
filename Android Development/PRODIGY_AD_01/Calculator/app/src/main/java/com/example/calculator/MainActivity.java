package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private TextView result;
    private String currentInput = "";
    private String lastResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        result = findViewById(R.id.result);

        setButtonListeners();
    }

    private void setButtonListeners() {
        int[] buttons = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.buttonadd, R.id.buttonsub,
                R.id.buttonmul, R.id.buttondiv, R.id.buttondot, R.id.buttonbl,
                R.id.buttonbr, R.id.buttonequal, R.id.buttonclearall, R.id.buttonclear
        };

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatButton button = (AppCompatButton) view;
                String buttonText = button.getText().toString();
                handleButtonClick(buttonText);
            }
        };

        for (int id : buttons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "C":
                currentInput = "";
                lastResult = "";
                break;
            case "=":
                if (!currentInput.isEmpty()) {
                    lastResult = calculateResult(currentInput);
                    currentInput = "";
                }
                break;
            case "CE":
                if (!currentInput.isEmpty()) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                }
                break;
            default:
                // Prevent adding multiple decimal points in the same number
                if (buttonText.equals(".") && currentInput.endsWith(".")) {
                    break;
                }
                currentInput += buttonText;
                break;
        }
        updateDisplay();
    }

    private String calculateResult(String expression) {
        try {
            Expression e = new ExpressionBuilder(expression).build();
            double result = e.evaluate();
            if (Double.isInfinite(result) || Double.isNaN(result)) {
                return "Error";
            }
            if (result == (long) result) {
                return String.format("%d", (long) result);
            } else {
                return String.format("%s", result);
            }
        } catch (Exception e) {
            return "Error";
        }
    }

    private void updateDisplay() {
        display.setText(currentInput);
        result.setText(lastResult);
    }
}
