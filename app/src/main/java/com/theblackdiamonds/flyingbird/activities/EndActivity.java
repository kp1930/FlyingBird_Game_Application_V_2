package com.theblackdiamonds.flyingbird.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.theblackdiamonds.flyingbird.R;

public class EndActivity extends AppCompatActivity {

    TextView tvYourScore, tvHighScore;
    int score, highScore;
    Button btnReturn;
    boolean isHigh = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);

        tvYourScore = findViewById(R.id.textViewYourScore);
        tvYourScore.setText("Score : " + score);

        tvHighScore = findViewById(R.id.textViewHighScore);

        SharedPreferences preferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        highScore = preferences.getInt("highScore", 0);
        if (score > highScore) {
            isHigh = true;
            SharedPreferences.Editor editor = getSharedPreferences("PREFERENCES", MODE_PRIVATE).edit();
            editor.putInt("highScore", score);
            editor.apply();
            tvHighScore.setText("Highest Score : " + score);
        } else {
            isHigh = false;
            tvHighScore.setText("Highest Score : " + highScore);
        }

        btnReturn = findViewById(R.id.buttonReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EndActivity.this, StartActivity.class);
                if (isHigh)
                    intent.putExtra("highScore", score);
                else
                    intent.putExtra("highScore", highScore);
                startActivity(intent);
            }
        });
    }
}