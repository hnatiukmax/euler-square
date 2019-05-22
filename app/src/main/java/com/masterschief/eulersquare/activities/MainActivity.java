package com.masterschief.eulersquare.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.masterschief.eulersquare.R;
import com.masterschief.eulersquare.logic.*;

public class MainActivity extends AppCompatActivity {
    private RadioGroup rdSize;
    private RadioGroup rdComplexity;
    private Button btnGo;
    private Mode mode;

    private RadioGroup.OnCheckedChangeListener listener_size = (RadioGroup group, int id) -> {
        switch (id) {
            case R.id.x3:
                mode.size = Size.X3;
                break;
            case R.id.x4:
                mode.size = Size.X4;
                break;
            case R.id.x5:
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


    private View.OnClickListener listener_go = (View v) -> {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("mode", mode);
        startActivity(intent);
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mode = new Mode(Size.X3, Level.EASY);

        btnGo = findViewById(R.id.go);
        btnGo.setOnClickListener(listener_go);

        rdSize = findViewById(R.id.rdSize);
        rdComplexity = findViewById(R.id.rdComplexity);

        rdSize.setOnCheckedChangeListener(listener_size);
        rdComplexity.setOnCheckedChangeListener(listener_level);
    }
}
