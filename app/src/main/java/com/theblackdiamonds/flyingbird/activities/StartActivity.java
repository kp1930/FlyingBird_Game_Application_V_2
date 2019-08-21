package com.theblackdiamonds.flyingbird.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.theblackdiamonds.flyingbird.R;
import com.theblackdiamonds.flyingbird.views.GameView;

public class StartActivity extends AppCompatActivity {

    TextView tvHighScore;
    Button btnStart;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        tvHighScore = findViewById(R.id.textViewScore);
        tvHighScore.setText("Highest Score : " + GameView.score);

        btnStart = findViewById(R.id.buttonStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
            }
        });
    }
}