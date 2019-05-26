package com.masterschief.eulersquare.controller;

import android.content.Context;
import android.graphics.Paint;
import android.media.Image;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

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
    private ImageView restart;
    private ImageView newGame;
    private Desk viewDesk;
    private Mode mode;

    //game var
    private Pair[][] copyGameDesk;
    private Pair[][] gameDesk;
    private int size;
    private LSquare sourceDesk;
    private Pair currentCell;
    private int countCell;

    public GameController(Context context, ArrayList<ImageView> buttonA, ArrayList<ImageView> buttonN,
                          ImageView hint, ImageView restart, ImageView newGame,  Desk viewDesk, Mode mode) {
        this.viewDesk = viewDesk;
        this.mode = mode;

        this.context = context;
        this.buttonA = buttonA;
        this.buttonN = buttonN;
        this.hint = hint;
        this.restart = restart;
        this.newGame = newGame;
    }

    /*
    Listeners
        View Desk
        Choice
        Hint
        Restart
        New Game
     */

    private View.OnTouchListener listener_desk = (View view, MotionEvent event) ->  {
        if (SystemClock.elapsedRealtime() - mLastClickTime < CLICK_INTERVAL){
            return false;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        Pair tmp = new Pair(
                (int) (event.getY() / (view.getWidth() / size)),
                (int) (event.getX() / (view.getWidth() / size))
        );

        if (tmp.equals(currentCell)) {
            currentCell = null;
        } else {
            currentCell = tmp;
        }

        viewDesk.setCurrentCell(currentCell);
        viewDesk.invalidate();

        //log.info("point : " + currentCell);
        return true;
    };

    private View.OnClickListener listener_choice = (View v) -> {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.blink);
        v.startAnimation(animation);

        if (SystemClock.elapsedRealtime() - mLastClickTime < CLICK_INTERVAL){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        if (currentCell == null) {
            Toast toast = Toast.makeText(context,
                    "Не выбрана ячейка!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 25);
            toast.show();
            return;
        }

        int choice = buttonA.indexOf(v) == -1 ? buttonN.indexOf(v) : buttonA.indexOf(v);
        choice++;
        boolean position = buttonA.contains(v);
        //log.info("listener choice (" + choice + "," + position + ")");
        if (isCorrect(choice, position)) {

            if (position) {
                if (gameDesk[currentCell.first][currentCell.second].first != 0) countCell--;
                gameDesk[currentCell.first][currentCell.second].first = choice;
            } else {
                if (gameDesk[currentCell.first][currentCell.second].second != 0) countCell--;
                gameDesk[currentCell.first][currentCell.second].second = choice;
            }
            countCell++;
            if (countCell == size*size*2) {
                log.info("CHECK WIN");
                if (checkWin()) {
                    log.info("WIN WIN WIN");
                    viewDesk.setOnClickListener(null);
                    viewDesk.setWin(true);
                    viewDesk.invalidate();
                }
            }

            //printPairs(gameDesk);
            viewDesk.invalidate();
            log.info("CountCell is " + countCell);
        }
    };

    private View.OnClickListener listener_hint = (View v) -> {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.blink);
        v.startAnimation(animation);

        if (gameDesk[currentCell.first][currentCell.second].first != 0 &&
                gameDesk[currentCell.first][currentCell.second].second != 0) {
            Toast toast = Toast.makeText(context,
                    "Ячейка заполнена!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 25);
            toast.show();
            return;
        } else if (gameDesk[currentCell.first][currentCell.second].first == 0) {
            countCell++;
            gameDesk[currentCell.first][currentCell.second].first =
                    sourceDesk.getEulerSquare()[currentCell.first][currentCell.second].first;
        } else {
            countCell++;
            gameDesk[currentCell.first][currentCell.second].second =
                    sourceDesk.getEulerSquare()[currentCell.first][currentCell.second].second;
        }
        if (countCell == size*size*2) {
            log.info("CHECK WIN");
            if (checkWin()) {
                log.info("WIN WIN WIN");
                viewDesk.setOnClickListener(null);
                viewDesk.setWin(true);
                viewDesk.invalidate();
            }
        }
        viewDesk.invalidate();
        //viewDesk.setMove();
    };

    private View.OnClickListener listener_newGame = (View v) -> {
        if (SystemClock.elapsedRealtime() - mLastClickTime < CLICK_INTERVAL){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.blink);
        v.startAnimation(animation);

        updateCountCell();

        if (viewDesk.isWin()) {
            viewDesk.setWin(false);
        }

            sourceDesk = new LSquare(size);
            prepareDesk(sourceDesk.getEulerSquare());

            copyGameDesk = new Pair[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    copyGameDesk[i][j] = new Pair(
                            gameDesk[i][j].first,
                            gameDesk[i][j].second
                    );
                }
            }

            viewDesk.setParameters(gameDesk, size);
            viewDesk.invalidate();

    };

    private View.OnClickListener listener_restart = (View v) -> {
        printPairs(sourceDesk.getEulerSquare());
        if (SystemClock.elapsedRealtime() - mLastClickTime < CLICK_INTERVAL){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.blink);
        v.startAnimation(animation);

        updateCountCell();

        if (viewDesk.isWin()) {
            viewDesk.setWin(false);
//            viewDesk.invalidate();
        }

        gameDesk = new Pair[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameDesk[i][j] = new Pair(
                        copyGameDesk[i][j].first,
                        copyGameDesk[i][j].second
                );
            }
        }

        viewDesk.setParameters(gameDesk, size);
        viewDesk.invalidate();
    };

    private void updateCountCell() {
        countCell = (int) (((double) mode.level.getComplexity() / 100) * Math.pow(size, 2));
        countCell += countCell/2;
    }

    private void printPairs(Pair[][] desk) {
        String str = "";
        log.info("Desk Print pairs\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                str += "(" + desk[i][j].first + ", " + desk[i][j].second + ")" + "  ";
            }
            log.info(str);
            str = "";
            System.out.println();
        }
    }

    public void start() {
        hint.setOnClickListener(listener_hint);
        viewDesk.setOnTouchListener(listener_desk);
        restart.setOnClickListener(listener_restart);
        newGame.setOnClickListener(listener_newGame);

        for (int i = 0; i < buttonA.size(); i++) {
            buttonA.get(i).setOnClickListener(listener_choice);
            buttonN.get(i).setOnClickListener(listener_choice);
        }

        size = mode.size.getSize();

        countCell = (int) (((double) mode.level.getComplexity() / 100) * Math.pow(size, 2));
        countCell += countCell/2;


        sourceDesk = new LSquare(size);
        prepareDesk(sourceDesk.getEulerSquare());

        //printPairs(sourceDesk.getEulerSquare());
        //printPairs(gameDesk);

        copyGameDesk = new Pair[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copyGameDesk[i][j] = new Pair(
                        gameDesk[i][j].first,
                        gameDesk[i][j].second
                );
            }
        }

        viewDesk.setParameters(gameDesk, size);
    }

    private void prepareDesk(Pair[][] fullDesk) {
//        int levelCount = (int) (((double) mode.level.getComplexity() / 100) * Math.pow(size, 2));
//        levelCount += levelCount/2;

        gameDesk = new Pair[size][size];
        log.info("Prepare size " +  countCell);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameDesk[i][j] = new Pair(0,0);
            }
        }

        for (int k = 0; k < countCell; k++) {
            int i = randomCell();
            int j = randomCell();
            //true - first | false - second
            boolean position = randomBoolean();

            if (position && gameDesk[i][j].first == 0) {
                gameDesk[i][j].first = fullDesk[i][j].first;
            } else if (gameDesk[i][j].second == 0) {
                gameDesk[i][j].second = fullDesk[i][j].second;
            } else {
                k--;
            }
        }
    }

    private boolean isCorrect(int value, boolean position) {
        if (position) {
            for (int i = 0; i < size; i++) {
                if (gameDesk[currentCell.first][i].first == value || gameDesk[i][currentCell.second].first == value) {
                    log.info("F. value = " + value + " | currentCell.first "  + currentCell.first + " | i" + i);
                    return false;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (gameDesk[currentCell.first][i].second == value || gameDesk[i][currentCell.second].second == value) {
                    log.info("S. value = " + value + " | currentCell.first "  + currentCell.second + " | i" + i);
                    return false;
                }
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

    public boolean checkWin() {
        int sum = (int) ((1 + size) / 2.0 * size);
        int j_sumF = 0;
        int i_sumF = 0;
        int j_sumS = 0;
        int i_sumS = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                i_sumF += gameDesk[i][j].first;
                j_sumF += gameDesk[i][j].first;
                i_sumS += gameDesk[i][j].second;
                j_sumS += gameDesk[i][j].second;
            }
            if (i_sumF != sum || j_sumF != sum|| i_sumS != sum|| j_sumS != sum) {
                return false;
            }
            i_sumF = j_sumF = i_sumS = j_sumS = 0;
        }

        ArrayList<Pair> list = new ArrayList(size * size);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                list.add(gameDesk[i][j]);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            Pair comp = list.get(i);
            list.remove(i);
            if (list.contains(comp))
                return false;
        }

        return true;
    }
}
