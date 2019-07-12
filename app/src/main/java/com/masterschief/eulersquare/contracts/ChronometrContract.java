package com.masterschief.eulersquare.contracts;

public interface ChronometrContract {
    public void onHint(long miliseconds);

    public void onStart();

    public void onPause();

    public long getBase();

    public String getTime();

    public void setWin();
}
