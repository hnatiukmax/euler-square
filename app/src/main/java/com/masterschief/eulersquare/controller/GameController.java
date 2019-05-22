package com.masterschief.eulersquare.controller;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

import com.masterschief.eulersquare.logic.LSquare;
import com.masterschief.eulersquare.logic.Mode;
import com.masterschief.eulersquare.logic.Pair;
import com.masterschief.eulersquare.ui.Desk;

import java.util.logging.Logger;

public class GameController {
    private Logger log = Logger.getLogger(GameController.class.getName());
    private final long CLICK_INTERVAL = 500;
    private long mLastClickTime;

    private Desk viewDesk;
    private Mode mode;

    private Pair[][] gameDesk;
    private int size;
    private LSquare sourceDesk;

    private View.OnTouchListener listener_move = (View view, MotionEvent event) -> {
        if (SystemClock.elapsedRealtime() - mLastClickTime < CLICK_INTERVAL){
            return false;
        }
        mLastClickTime = SystemClock.elapsedRealtime();


        return true;
    };

    public GameController(Desk viewDesk, Mode mode) {
        this.viewDesk = viewDesk;
        this.mode = mode;

        size = mode.size.getSize();
        sourceDesk = new LSquare(size);

        prepareDesk(sourceDesk.getEulerSquare());
    }

    public void start() {
        viewDesk.setParameters(gameDesk, size);
    }

    private void prepareDesk(Pair[][] fullDesk) {
        int levelCount = (int) (mode.level.getComplexity() / 100) * size * size;
        gameDesk = new Pair[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameDesk[i][j] = new Pair(0,0);
            }
        }

        for (int k = 0; k < levelCount; k++) {
            int i = randomCell();
            int j = randomCell();
            //true - first | false - second
            boolean position = randomBoolean();

            if (position && gameDesk[i][j].first == 0) {
                gameDesk[i][j].first = fullDesk[i][j].first;
            } else if (gameDesk[i][j].second == 0) {
                gameDesk[i][j].second = fullDesk[i][j].second;
            } else {
                levelCount++;
            }
        }
    }

    private boolean isCorrect(Pair point, int value, boolean position) {
        if (gameDesk[point.first][point.second].first != 0 && gameDesk[point.first][point.second].second != 0) {
            return false;
        }

        if (position) {
            for (int i = 0; i < size; i++) {
                if (gameDesk[point.first][i].first == value || gameDesk[i][point.second].first == value)
                    return false;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (gameDesk[point.first][i].second == value || gameDesk[i][point.second].second == value)
                    return false;
            }
        }

        return true;
    }

    private int randomCell() {
        return (int) (Math.random() * 8);
    }

    private boolean randomBoolean(){
        return Math.random() < 0.5;
    }
}
