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
    private Pair currentCell;
    private int size;

    public Desk(Context context) {
        super(context);
    }

    public Desk(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Desk(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setMove(int choice, boolean position) {
        if (position) {
            desk[currentCell.first][currentCell.second].first = choice;
        } else {
            desk[currentCell.first][currentCell.second].second = choice;
        }
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
                break;
            case 4:
                backRes = R.drawable.x4image;
                break;
            case 5:
                backRes = R.drawable.x5image;
                break;
        }
    }

    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        setBackgroundResource(backRes);
        drawCurrentCell(canvas);
        //drawCurrentSquare(canvas);

//        int wh = (int) (canvas.getWidth() / size / 2.0);
//        int range = (int) (canvas.getWidth() / size);
////
//        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
//        p.setTextSize(wh );
//        p.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
//        //p.setStyle(Paint.Style.STROKE);
//
//        canvas.drawText("B", (float) (range / 7.0), (float) (range / 3) * 2, p);
//        canvas.drawText("2", (float) (range / 5.0) + p.measureText("B"), (float) (range / 3) * 2, p);
//        log.info("cv w = " +canvas.getWidth() + " " +(float) canvas.getWidth() / size + " x = " + (float) (range / 6.0) + " y = " + (float) (range / 3.0));



//        canvas.drawBitmap(
//                Bitmap
//                        .createScaledBitmap(
//                                getBitMap(2, true), wh, wh, false),
//                (int) (range / 6),
//                (int) (range / 3),
//                new Paint()
//        );
//

//
//        Bitmap a = BitmapFactory.decodeResource(getResources(), R.drawable.button1);
//        canvas.drawBitmap(Bitmap.createScaledBitmap(a, 100, 100, false),
//                (getWidth() / 3) + 5, 50, new Paint());

    }

    protected void drawCurrentSquare(Canvas cv) {
        int range = cv.getWidth() / size;

        Paint fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setTextSize((int) (range / 2.2));
        fontPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                log.info("trouble");
                if (desk[i][j].first != 0) {
                    //log.info("char is " + (desk[i][j].first + 16) + " " + (char) 65) ;
                    cv.drawText(
                            Character.toString((char) (desk[i][j].first + 64)),
                            (float) (range / 7.0) + (range * i),
                            (float) (range / 3) * 2 + (range * i),
                            fontPaint
                    );
                }
                if (desk[i][j].second != 0) {
                    cv.drawText(
                            Integer.toString(desk[i][j].second),
                            (float) (range / 5.0) + fontPaint.measureText("B") + (range * i),
                            (float) (range / 3) * 2 + (range * i),
                            fontPaint
                    );
                }
            }
        }
    }

    protected void drawCurrentCell(Canvas cv) {
        Paint pCell = new Paint();
        pCell.setColor(0x27E23737);

        Paint pFriend = new Paint();
        pFriend.setColor(0x3251C5C5);

        float top, bottom, left, right, range;

        if (currentCell != null) {
            log.info("draw current Cell (" + currentCell.first + ", " + currentCell.second + ")");

            range = cv.getWidth() / size;

            top = currentCell.second * range + 4;
            bottom = top + range - 4;
            left = currentCell.first * range + 4;
            right = left + range - 4;

            for (int i = 0; i < size; i++) {
                cv.drawRect(i * range + 5,top, range*(i+1), bottom, pFriend);
                cv.drawRect(left, i * range + 5, right, range*(i+1), pFriend);
            }

            cv.drawRect(left, top, right, bottom, pFriend);

        } else {
            log.info("current Cell is null ");
        }
    }
}

