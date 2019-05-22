package com.masterschief.eulersquare.logic;

public enum Level {
    EASY(1),
    MEDIUM(2),
    HARD(3);

    private int comp;

    Level(int comp) {
        this.comp = comp;
    }

    public int getComplexity() {
        return comp;
    }
}
