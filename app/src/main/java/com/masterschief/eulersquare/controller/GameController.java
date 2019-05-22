package com.masterschief.eulersquare.controller;

import com.masterschief.eulersquare.logic.Mode;
import com.masterschief.eulersquare.logic.Pair;
import com.masterschief.eulersquare.ui.Desk;

public class GameController {
    private Desk viewDesk;

    private Pair[][] gameDesk;
    private Mode mode;

    public GameController(Desk viewDesk, Pair[][] fullDesk, Mode mode) {
        this.viewDesk = viewDesk;
        this.mode = mode;

        prepareDesk(fullDesk);
    }

    public void start() {
        viewDesk.setParameters(gameDesk);
    }

    private void prepareDesk(Pair[][] fullDesk) {

    }
}
