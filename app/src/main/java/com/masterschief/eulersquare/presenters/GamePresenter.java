package com.masterschief.eulersquare.presenters;

import com.masterschief.eulersquare.contracts.GameContract;
import com.masterschief.eulersquare.logic.LSquare;
import com.masterschief.eulersquare.logic.Mode;
import com.masterschief.eulersquare.logic.Pair;
import com.masterschief.eulersquare.utils.Utils;

import java.util.ArrayList;

public class GamePresenter implements GameContract.GamePresenterContract {

    GameContract.GameViewContract view;

    private Mode mode;

    private Pair[][] copyGameDesk;
    private Pair[][] gameDesk;
    private LSquare sourceDesk;
    private int countCell;
    private int size;

    public GamePresenter(Mode mode) {
        this.mode = mode;
        this.size = mode.size.getSize();

        countCell = (int) (((double) mode.level.getComplexity() / 100) * Math.pow(size, 2));
        countCell += countCell/2;

        sourceDesk = new LSquare(size);
        prepareDesk(sourceDesk.getEulerSquare());
        setCopyGameDesk();
    }

    /*
    /   GamePresenterContract methods
    */

    @Override
    public void attachView(GameContract.GameViewContract view) {
        this.view = view;

        view.setSource(gameDesk);
        view.updateDesk();
        setCopyGameDesk();
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void onNewGame() {
        prepareDesk(sourceDesk.getMixEulerSquare());

        view.setSource(gameDesk);
        view.updateDesk();
        setCopyGameDesk();
    }

    @Override
    public void onRestartGame() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameDesk[i][j] = new Pair(
                        copyGameDesk[i][j].first,
                        copyGameDesk[i][j].second
                );
            }
        }

        view.setSource(gameDesk);
        view.updateDesk();
    }

    @Override
    public void onPauseGame() {
        view.setPause();
    }

    @Override
    public void onAction(int value, boolean position) {
        if (view.getCurrentCell().first == -1) {
            view.showToast("Поле не выбрано!");
        } else if(isCorrect(++value, position)) {
            if (position) {
                if (gameDesk[view.getCurrentCell().first][view.getCurrentCell().second].first != 0) countCell--;
                gameDesk[view.getCurrentCell().first][view.getCurrentCell().second].first = value;
            } else {
                if (gameDesk[view.getCurrentCell().first][view.getCurrentCell().second].second != 0) countCell--;
                gameDesk[view.getCurrentCell().first][view.getCurrentCell().second].second = value;
            }
            countCell++;
            view.updateDesk();
            checkWin();
        }
    }

    @Override
    public boolean onHint() {
        if (view.getCurrentCell().first == -1 || view.getCurrentCell() == null) {
            view.showToast("Поле не выбрано!");
            return false;
        } else if (gameDesk[view.getCurrentCell().first][view.getCurrentCell().second].first != 0 &&
                    gameDesk[view.getCurrentCell().first][view.getCurrentCell().second].second != 0) {
            view.showToast("Ячейка заполнена!");
            return false;
        } else if (gameDesk[view.getCurrentCell().first][view.getCurrentCell().second].first == 0) {
            gameDesk[view.getCurrentCell().first][view.getCurrentCell().second].first =
                    sourceDesk.getEulerSquare()[view.getCurrentCell().first][view.getCurrentCell().second].first;
        } else {
            gameDesk[view.getCurrentCell().first][view.getCurrentCell().second].second =
                    sourceDesk.getEulerSquare()[view.getCurrentCell().first][view.getCurrentCell().second].second;
        }
        countCell++;
        view.updateDesk();
        checkWin();
        return true;
    }

    /*
    /  private method's
    */

    private void checkWin() {
        if (countCell == size * size * 2 && isWin()) {
            view.setWinDeskState();
        }
    }

    private void prepareDesk(Pair[][] fullDesk) {
        int complexity =  (int) (((double) mode.level.getComplexity() / 100) * Math.pow(size, 2));
        complexity += complexity/2;

        gameDesk = new Pair[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameDesk[i][j] = new Pair(0,0);
            }
        }

        for (int k = 0; k < complexity; k++) {
            int i = (int) (Math.random() * size);
            int j = (int) (Math.random() * size);
            //true - first | false - second
            boolean position = Math.random() < 0.5;

            if (position && gameDesk[i][j].first == 0) {
                gameDesk[i][j].first = fullDesk[i][j].first;
            } else if (gameDesk[i][j].second == 0) {
                gameDesk[i][j].second = fullDesk[i][j].second;
            } else {
                k--;
            }
        }
    }

    private boolean isWin() {
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

    private boolean isCorrect(int value, boolean position) {
        ArrayList<Pair> list = new ArrayList(size*size);
        int curentFirst = view.getCurrentCell().first;
        int curentSecond = view.getCurrentCell().second;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (gameDesk[i][j].first != 0 && gameDesk[i][j].second != 0 )
                    list.add(gameDesk[i][j]);
            }
        }

        Pair compare = new Pair(
                position ? value : gameDesk[curentFirst][curentSecond].first,
                position ? gameDesk[curentFirst][curentSecond].second : value
        );

        if (list.contains(compare)) {
            return false;
        }

        if (position) {
            for (int i = 0; i < size; i++) {
                if (gameDesk[curentFirst][i].first == value || gameDesk[i][curentSecond].first == value) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (gameDesk[curentFirst][i].second == value || gameDesk[i][curentSecond].second == value) {
                    return false;
                }
            }
        }

        return true;
    }

    private void setCopyGameDesk() {
        copyGameDesk = new Pair[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copyGameDesk[i][j] = new Pair(
                        gameDesk[i][j].first,
                        gameDesk[i][j].second
                );
            }
        }
    }
}
