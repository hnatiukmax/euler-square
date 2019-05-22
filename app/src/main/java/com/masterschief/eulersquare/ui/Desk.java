package com.masterschief.eulersquare.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.masterschief.eulersquare.R;
import com.masterschief.eulersquare.logic.Level;
import com.masterschief.eulersquare.logic.Mode;
import com.masterschief.eulersquare.logic.Pair;
import com.masterschief.eulersquare.logic.Size;

public class Desk extends View {
    private Pair[][] desk;
    private int backRes;
    private Pair currentCell;

    public Desk(Context context) {
        super(context);
    }

    public Desk(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Desk(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setParameters(Pair[][] desk, int size) {
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

    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint();
        super.onDraw(canvas);

        setBackgroundResource(backRes);

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.logo2);
        b.createScaledBitmap(b, 100, 100, false);
        canvas.drawBitmap(b.createScaledBitmap(b, 100, 100, false),
                (getWidth() / 3) + 5, 50, p);

        //drawDesk(canvas);
    }

    protected void drawDesk(Canvas cv) {
        if (currentCell != null) {
            //fill cell someth color
        }


    }
}

