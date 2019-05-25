package com.masterschief.eulersquare.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    @SuppressLint("ResourceType")
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        setBackgroundResource(backRes);

        drawCurrentCell(canvas);
        //drawCurrentSquare();


//        Bitmap a = BitmapFactory.decodeResource(getResources(), R.drawable.button1);
//        canvas.drawBitmap(a.createScaledBitmap(b, 100, 100, false),
//                (getWidth() / 3) + 5, 50, new Paint());

    }

    protected void drawCurrentCell(Canvas cv) {
        Paint p = new Paint();
        p.setColor(0x7581F3F3);
        p.setStrokeWidth(2);

        if (currentCell != null) {

            float range = cv.getWidth() / size;
            float left = currentCell.first * range + 5;
            float top = currentCell.second * range + 5;
            float right = left + range - 5;
            float bottom = top + range - 5;

            log.info("DrawCell |");

            cv.drawRect(left, top, right, bottom, p);
        }
    }

    class BitMapGenerate extends AsyncTask<> {

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }
    }
}

