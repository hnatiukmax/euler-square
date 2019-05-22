package com.masterschief.eulersquare.logic;

import java.io.Serializable;

public class Mode implements Serializable {
    public Size size;
    public Level level;

    public Mode(Size size, Level level) {
        this.size = size;
        this.level = level;
    }
}
