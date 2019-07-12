package com.masterschief.eulersquare.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.masterschief.eulersquare.BuildConfig;
import com.masterschief.eulersquare.R;
import com.masterschief.eulersquare.logic.*;
import com.masterschief.eulersquare.views.GameView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rdComplexity)
    RadioGroup rdComplexity;

    @BindView(R.id.bntGo)
    Button btnGo;

    @BindView(R.id.rdSize)
    NumberPicker rdSize;

    private Mode mode;

    private NumberPicker.OnValueChangeListener rdSize_listener = (NumberPicker picker, int oldVal, int newVal) -> {
        switch (newVal) {
            case 3:
                mode.size = Size.X3;
                break;
            case 4:
                mode.size = Size.X4;
                break;
            case 5:
                mode.size = Size.X5;
                break;
        }

    };

    private RadioGroup.OnCheckedChangeListener listener_level = (RadioGroup group, int id) -> {
        switch (id) {
            case R.id.easy:
                mode.level = Level.EASY;
                break;
            case R.id.medium:
                mode.level = Level.MEDIUM;
                break;
            case R.id.hard:
                mode.level = Level.HARD;
                break;
        }
    };

    @OnClick(R.id.bntGo)
    void go_listener() {
        Intent intent = new Intent(this, GameView.class);
        intent.putExtra("mode", mode);
        startActivity(intent);
        Animatoo.animateSlideLeft(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mode = (Mode) savedInstanceState.getSerializable("mode");
        } else {
            mode = new Mode(Size.X4, Level.EASY);
            rdSize.setValue(4);
        }

        rdSize.setMinValue(3);
        rdSize.setMaxValue(5);

        rdComplexity.setOnCheckedChangeListener(listener_level);
        rdSize.setOnValueChangedListener(rdSize_listener);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("mode", mode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        rdSize.setValue(mode.size.getSize());
    }
}
