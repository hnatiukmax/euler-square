package com.masterschief.eulersquare.contracts;

import com.masterschief.eulersquare.logic.Pair;
import com.masterschief.eulersquare.ui.Desk;

public interface DeskContract {

    public void setWin();
    public void setWin(String time);

    public void setPause();

    public void updateDesk();

    public void initDesk(Pair[][] desk, Pair currentCell, int size);

    public Desk.TypeUpdate getCurrentStateUpdate();
}
