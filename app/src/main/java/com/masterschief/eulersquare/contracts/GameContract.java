package com.masterschief.eulersquare.contracts;

import com.masterschief.eulersquare.logic.Pair;

public interface GameContract {

    interface GameViewContract {
        public void updateDesk();

        public void setWinDeskState();

        public void setPause();

        public void showToast(String message);

        Pair getCurrentCell();

        void setSource(Pair[][] desk);
    }

    interface GamePresenterContract {
        public void attachView(GameViewContract view);

        public void detachView();

        public void onNewGame();

        public void onRestartGame();

        public void onPauseGame();

        void onAction(int value, boolean position);

        public boolean onHint();
    }
}
