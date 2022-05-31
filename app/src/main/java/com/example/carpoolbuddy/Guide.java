package com.example.carpoolbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Guide extends AppCompatActivity {

    private TextView welcome;
    private TextView instruct;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        welcome = findViewById(R.id.welcome);
        instruct = findViewById(R.id.instruct);

        welcome.setText("Welcome to the Mark Zuckerberg Fishing Game!");
        instruct.setText("This is your chance to win 1,000,000 dollars. All you have to do is answer a few simple questions. Every time you click submit, the prize money is doubled, starting at $2. There are a total of 20 levels, with the last level rewarding over 1 million dollars. Good Luck!");
    }

    public void begin(View v) {
        Intent intent = new Intent(this, FreeMoney.class);
        startActivity(intent);
    }
}