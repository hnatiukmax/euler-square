package com.masterschief.eulersquare.controller;

import android.content.Context;
import android.media.Image;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.masterschief.eulersquare.R;
import com.masterschief.eulersquare.logic.LSquare;
import com.masterschief.eulersquare.logic.Mode;
import com.masterschief.eulersquare.logic.Pair;
import com.masterschief.eulersquare.ui.Desk;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Logger;

public class GameController {
    //system var
    private Logger log = Logger.getLogger(GameController.class.getName());
    private final long CLICK_INTERVAL = 200;
    private long mLastClickTime;


    //parametrs
    private Context context;
    ArrayList<ImageView> buttonA;
    ArrayList<ImageView> buttonN;
    private ImageView hint;
    private Desk viewDesk;
    private Mode mode;

    //game var
    private Pair[][] gameDesk;
    private int size;
    private LSquare sourceDesk;
    private Pair currentCell;

    private View.OnTouchListener listener_desk = (View view, MotionEvent event) -> {
        if (SystemClock.elapsedRealtime() - mLastClickTime < CLICK_INTERVAL){
            return false;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        Pair tmp = new Pair(
                (int) (event.getX() / (view.getWidth() / size)),
                (int) (event.getY() / (view.getWidth() / size))
        );

        if (tmp.equals(currentCell)) {
            currentCell = null;
        } else {
            currentCell = tmp;
        }

        viewDesk.setCurrentCell(currentCell);
        viewDesk.invalidate();

        log.info("point : " + currentCell);
        return true;
    };

    private View.OnClickListener listener_choice = (View v) -> {
          int choice = buttonA.indexOf(v) == -1 ? buttonN.indexOf(v) : buttonA.indexOf(v);
          choice++;
          boolean position = buttonA.contains(v);
          if (isCorrect(choice, position)) {
                viewDesk.setMove(choice, position);
                viewDesk.invalidate();
          }
    };

    public GameController(Context context, ArrayList<ImageView> buttonA, ArrayList<ImageView> buttonN,
                          ImageView hint, Desk viewDesk, Mode mode) {
        this.viewDesk = viewDesk;
        this.mode = mode;

        this.context = context;
        this.buttonA = buttonA;
        this.buttonN = buttonN;
        this.hint = hint;
    }

    private void printPairs() {
        String str = "";
        log.info("Print pairs\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                str += "(" + gameDesk[i][j].first + ", " + gameDesk[i][j].second + ")" + "  ";
            }
            log.info(str);
            str = "";
            System.out.println();
        }
    }

    public void start() {
        viewDesk.setOnTouchListener(listener_desk);

        size = mode.size.getSize();
        sourceDesk = new LSquare(size);


        prepareDesk(sourceDesk.getEulerSquare());

        viewDesk.setParameters(gameDesk, size);
        printPairs();

        printPairs();
    }

    private void prepareDesk(Pair[][] fullDesk) {
        int levelCount = (int) (((double) mode.level.getComplexity() / 100) * Math.pow(size, 2));
        levelCount += levelCount/2;

        gameDesk = new Pair[size][size];
        log.info("Prepare size " +  levelCount);

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

    private boolean isCorrect(int value, boolean position) {
        if (position) {
            for (int i = 0; i < size; i++) {
                if (gameDesk[currentCell.first][i].first == value || gameDesk[i][currentCell.second].first == value)
                    return false;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (gameDesk[currentCell.first][i].second == value || gameDesk[i][currentCell.second].second == value)
                    return false;
            }
        }

        return true;
    }

    private int randomCell() {
        return (int) (Math.random() * size);
    }

    private boolean randomBoolean(){
        return Math.random() < 0.5;
    }
}
