package com.masterschief.eulersquare.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.masterschief.eulersquare.R;
import com.masterschief.eulersquare.contracts.DeskContract;
import com.masterschief.eulersquare.logic.Pair;


public class Desk extends View implements DeskContract {
    public enum TypeUpdate {
        WIN,
        PAUSE,
        JUST

        }
    private TypeUpdate currentStateUpdate;
    private String time;

    //background image state game
    private int backRes;
    private int winBackRes;
    private int pauseBackRes;


    //desk params
    private Pair[][] desk;
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

    public TypeUpdate getCurrentStateUpdate() {
        return currentStateUpdate;
    }

    /*
    /gameDeskContract method's
     */

    @Override
    public void setWin() {
        this.setWin("");
    }

    @Override
    public void setWin(String time) {
        this.time = time;
        currentStateUpdate = TypeUpdate.WIN;
        invalidate();
    }

    @Override
    public void setPause() {
        currentStateUpdate = TypeUpdate.PAUSE;
        invalidate();
    }

    @Override
    public void updateDesk() {
        currentStateUpdate = TypeUpdate.JUST;
        invalidate();
    }

    //initialization

    public void initDesk(Pair[][] desk, Pair currentCell, int size) {
        this.desk = desk;
        this.currentCell = currentCell;
        this.size = size;

        setDeskSize();
    }


    /*
    /   drawing methods
    */

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (currentStateUpdate) {
            case WIN:
                setBackgroundResource(winBackRes);
                drawTime(canvas);
                break;
            case PAUSE:
                setBackgroundResource(pauseBackRes);
                break;
            case JUST:
                setBackgroundResource(backRes);

                drawCurrentCell(canvas);
                drawCurrentSquare(canvas);
                break;
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
                    //log.info("First | " + i + "," + j + " | " + desk[i][j].first);
                    cv.drawText(
                            Character.toString((char) (desk[i][j].first + 64)),
                            (float) ((range / 7.0) + (range * j)),
                            (float) ((range / 3) * 2 + (range * i)),
                            fontPaint
                    );
                }
                if (desk[i][j].second != 0) {
                    //log.info("Second | " + i + "," + j + " | " + desk[i][j].second);
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

        if (currentCell != null || (currentCell.first == -1 || currentCell.second == -1)) {
            //log.info("draw current Cell (" + currentCell.first + ", " + currentCell.second + ")");

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
            //log.info("current Cell is null ");
        }
    }

    private void drawTime(Canvas cv) {
        int range = cv.getWidth() / size;

        Paint fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setTextSize((int) (range / 2) * (size / 3));
        fontPaint.setColor(0xFF1A5848);
        fontPaint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD));

        cv.drawText(
                time,
                (cv.getWidth() / 2) - range /2  ,
                cv.getHeight() - range/3,
                fontPaint
        );
    }

    private void setDeskSize() {
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
}

