package com.theblackdiamonds.flyingbird.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

import com.theblackdiamonds.flyingbird.R;
import com.theblackdiamonds.flyingbird.activities.EndActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class GameView extends View {

    private int birdX = 10, birdY, birdSpeed, life_count = 3, level_count = 1, score;
    private Bitmap bgImage;
    private Bitmap[] life = new Bitmap[2];
    private Bitmap[] bird = new Bitmap[2];

    private Paint scorePaint = new Paint();
    private Paint levelPaint = new Paint();
    private boolean isTouch = false;

    private int blueX, blueY;
    private Paint bluePaint = new Paint();

    private int blackX, blackY;
    private Paint blackPaint = new Paint();

    public GameView(Context context) {
        super(context);
        bird[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bird1);
        bird[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bird2);
        bgImage = BitmapFactory.decodeResource(getResources(), R.drawable.bg);

        bluePaint.setColor(Color.BLUE);
        bluePaint.setAntiAlias(false);

        blackPaint.setColor(Color.BLACK);
        blackPaint.setAntiAlias(false);

        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(32);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        levelPaint.setColor(Color.DKGRAY);
        levelPaint.setTextSize(32);
        levelPaint.setTypeface(Typeface.DEFAULT_BOLD);
        levelPaint.setTextAlign(Paint.Align.CENTER);
        levelPaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_g);

        birdY = 500;
        score = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int canvasWidth = getWidth();
        int canvasHeight = getHeight();

        canvas.drawBitmap(bgImage, 0, 0, null);

        int minBirdY = bird[0].getHeight();
        int maxBirdY = canvasHeight - minBirdY * 3;
        birdY += birdSpeed;

        if (birdY < minBirdY) birdY = minBirdY;
        if (birdY > maxBirdY) birdY = maxBirdY;
        birdSpeed += 2;

        if (isTouch) {
            canvas.drawBitmap(bird[1], birdX, birdY, null);
            isTouch = false;
        } else {
            canvas.drawBitmap(bird[0], birdX, birdY, null);
        }

        int blueSpeed = 15;
        blueX -= blueSpeed;

        if (isHit(blueX, blueY)) {
            score += 10;
            blueX = -100;
        }

        if (blueX < 0) {
            blueX = canvasWidth + 20;
            blueY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        }
        canvas.drawCircle(blueX, blueY, 10, bluePaint);

        int blackSpeed = 20;
        blackX -= blackSpeed;
        if (isHit(blackX, blackY)) {
            blackX = -100;
            life_count--;

            if (life_count == 0) {
                @SuppressLint("DrawAllocation")
                Intent endIntent = new Intent(getContext(), EndActivity.class);
                endIntent.putExtra("score", score);
                endIntent.addFlags(FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(endIntent);
            }
        }

        if (blackX < 0) {
            blackX = canvasWidth + 200;
            blackY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        }
        canvas.drawCircle(blackX, blackY, 20, blackPaint);
        canvas.drawText("Score : " + score, 20, 60, scorePaint);

        while (score == (50 * level_count)) {
            level_count++;
        }
        canvas.drawText("Level : " + level_count, canvasWidth / 2, 60, levelPaint);

        for (int i = 0; i < 3; i++) {
            int x = (int) (800 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i < life_count) {
                canvas.drawBitmap(life[0], x, y, null);
            } else {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }
    }

    public boolean isHit(int x, int y) {
        return birdX < x && x < (birdX + bird[0].getWidth()) &&
                birdY < y && y < (birdY + bird[0].getHeight());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isTouch = true;
            birdSpeed = -20;
        }
        return true;
    }
}