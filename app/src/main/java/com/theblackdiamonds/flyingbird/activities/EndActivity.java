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

public class EndActivity extends AppCompatActivity {

    TextView tvYourScore, tvHighScore;
    int score = GameView.score;
    int highScore = score;
    Button btnReturn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        tvYourScore = findViewById(R.id.textViewYourScore);
        tvYourScore.setText("Score : " + score);

        tvHighScore = findViewById(R.id.textViewHighScore);
        tvHighScore.setText("Highest Score : " + highScore);

        btnReturn = findViewById(R.id.buttonReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EndActivity.this, MainActivity.class));
            }
        });
    }
}