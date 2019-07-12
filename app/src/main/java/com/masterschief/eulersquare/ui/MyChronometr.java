package com.masterschief.eulersquare.ui;

import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.Chronometer;

import com.masterschief.eulersquare.contracts.ChronometrContract;

import java.util.concurrent.TimeUnit;

public class MyChronometr extends Chronometer implements ChronometrContract {
    private long lastPause;

    public MyChronometr(Context context) {
        super(context);
    }

    public MyChronometr(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyChronometr(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyChronometr(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onHint(long miliseconds) {
        this.setBase(getBase() - miliseconds);
    }

    @Override
    public void onStart() {
        if (lastPause != 0) {
            this.setBase(getBase() + SystemClock.elapsedRealtime() - lastPause);
            this.start();
            lastPause = 0;
        } else {
            this.setBase(SystemClock.elapsedRealtime());
            this.start();
        }
    }

    @Override
    public void onPause() {
        lastPause = SystemClock.elapsedRealtime();
        this.stop();
    }

    @Override
    public String getTime() {
        long miliseconds = SystemClock.elapsedRealtime() - getBase();
        String time = String.format("%d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(miliseconds),
                TimeUnit.MILLISECONDS.toSeconds(miliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(miliseconds))
        );
        return time;
    }

    @Override
    public void setWin() {
        this.setBase(getBase() - (getBase() - SystemClock.elapsedRealtime()) );
        this.stop();
    }
}
