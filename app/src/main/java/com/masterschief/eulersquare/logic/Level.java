package com.masterschief.eulersquare.logic;

public enum Level {
    //percent of full desk
    EASY(75),
    MEDIUM(50),
    HARD(35);

    private int comp;

    Level(int comp) {
        this.comp = comp;
    }

    public int getComplexity() {
        return comp;
    }

}
