package com.masterschief.eulersquare.ui;

import android.content.Context;
import android.view.View;

import com.masterschief.eulersquare.logic.Level;
import com.masterschief.eulersquare.logic.Mode;
import com.masterschief.eulersquare.logic.Pair;
import com.masterschief.eulersquare.logic.Size;

public class Desk extends View {
    private Pair[][] desk;

    public Desk(Context context) {
        super(context);
    }

    public void setParameters(Pair[][] desk) {
        this.desk = desk;
    }

}

