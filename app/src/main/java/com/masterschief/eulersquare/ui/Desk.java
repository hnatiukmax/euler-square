package com.masterschief.eulersquare.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.masterschief.eulersquare.R;
import com.masterschief.eulersquare.controller.GameController;
import com.masterschief.eulersquare.logic.Pair;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class Desk extends View {
    private Logger log = Logger.getLogger(Desk.class.getName());

    private Pair[][] desk;
    private int backRes;
    private int winBackRes;
    private int pauseBackRes;
    private Pair currentCell;
    private int size;
    private boolean isWin = false;
    private boolean isPause = false;

    public void setPause(boolean pause) {
        isPause = pause;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setWin(boolean isWin) {
        this.isWin = isWin;
    }

    public boolean isWin() {
        return isWin;
    }

    public Desk(Context context) {
        super(context);
    }

    public Desk(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Desk(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setCurrentCell(Pair currentCell) {
        this.currentCell = currentCell;
    }

    public void setParameters(Pair[][] desk, int size) {
        this.size = size;
        this.desk = desk;

        switch (size) {
            case 3:
                backRes = R.drawable.x3image;
                winBackRes = R.drawable.x3imageblur;
                pauseBackRes = R.drawable.x3imagestop;
                break;
            case 4:
                backRes = R.drawable.x4image;
                winBackRes = R.drawable.x4imageblur;
                pauseBackRes = R.drawable.x4imagestop;
                break;
            case 5:
                backRes = R.drawable.x5image;
                winBackRes = R.drawable.x5imageblur;
                pauseBackRes = R.drawable.x5imagestop;
                break;
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isWin) {
            setBackgroundResource(winBackRes);
        } else if (isPause) {
            setBackgroundResource(pauseBackRes);
        } else {
            setBackgroundResource(backRes);

            drawCurrentCell(canvas);
            drawCurrentSquare(canvas);
        }
    }

    protected void drawCurrentSquare(Canvas cv) {
        int range = cv.getWidth() / size;

        Paint fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setTextSize((int) (range / 2.2));
        fontPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (desk[i][j].first != 0) {
                    log.info("First | " + i + "," + j + " | " + desk[i][j].first);
                    cv.drawText(
                            Character.toString((char) (desk[i][j].first + 64)),
                            (float) ((range / 7.0) + (range * j)),
                            (float) ((range / 3) * 2 + (range * i)),
                            fontPaint
                    );
                }
                if (desk[i][j].second != 0) {
                    log.info("Second | " + i + "," + j + " | " + desk[i][j].second);
                    cv.drawText(
                            Integer.toString(desk[i][j].second),
                            (float) ((range / 5.0) + fontPaint.measureText("B") + (range * j)),
                            (float) ((range / 3) * 2 + (range * i)),
                            fontPaint
                    );
                }
            }
        }
    }

    protected void drawCurrentCell(Canvas cv) {
        Paint pCell = new Paint();
        pCell.setColor(0x3C1DC9EC);

        Paint pFriend = new Paint();
        pFriend.setColor(0x3251C5C5);

        float top, bottom, left, right, range;

        if (currentCell != null) {
            log.info("draw current Cell (" + currentCell.first + ", " + currentCell.second + ")");

            range = cv.getWidth() / size;

            top = currentCell.first * range ;
            bottom = top + range ;
            left = currentCell.second * range ;
            right = left + range ;

            for (int i = 0; i < size; i++) {
                cv.drawRect(i * range ,top, range*(i+1), bottom, pFriend);
                cv.drawRect(left, i * range , right, range*(i+1), pFriend);
            }

            cv.drawRect(left, top, right, bottom, pCell);

        } else {
            log.info("current Cell is null ");
        }
    }
}

