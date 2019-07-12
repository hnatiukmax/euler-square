package com.masterschief.eulersquare.logic;

public enum Size {
    X3(3),
    X4(4),
    X5(5);

    private int size;

    Size(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }


}
