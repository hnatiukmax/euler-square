package com.masterschief.eulersquare.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.masterschief.eulersquare.BuildConfig;
import com.masterschief.eulersquare.R;
import com.masterschief.eulersquare.intro.IntroActivity;
import com.masterschief.eulersquare.utils.Preferences;

import timber.log.Timber;

public class SharedPreferencesActivity extends AppCompatActivity {
    private static final String MY_SETTINGS = "my_settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Preferences settings = new Preferences(this);
        if (settings.checkRunBefore()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, IntroActivity.class));
        }



    }
}
