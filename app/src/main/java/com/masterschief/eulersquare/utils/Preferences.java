package com.masterschief.eulersquare.utils;

import android.content.Context;

public class Preferences {

    private static final String MY_SETTINGS = "my_settings";

    android.content.SharedPreferences prefs;

    public Preferences(Context context) {
        this.prefs = context.getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
    }

    public boolean checkRunBefore() {
        if (prefs.getBoolean("hasRunBefore", false)) {
            return true;
        } else {
            prefs.edit().putBoolean("hasRunBefore", true).commit();
            return false;
        }
    }

    public void setSound(boolean value) {
        prefs.edit().putBoolean("sound", value).commit();
    }

    public void setVibro(boolean value) {
        prefs.edit().putBoolean("vibro", value).commit();
    }

    public boolean getSound() {
        return prefs.getBoolean("sound", true);
    }

    public boolean getVibro() {
        return prefs.getBoolean("vibro", true);
    }


}
