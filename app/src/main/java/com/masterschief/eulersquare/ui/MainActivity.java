package com.masterschief.eulersquare.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.masterschief.eulersquare.R;

public class MainActivity extends AppCompatActivity {
    private CheckBox x3, x4, x5;
    private CheckBox easy, medium, hard;
    private Button go;
    private String size = "";
    private String comp = "";

    private CompoundButton.OnCheckedChangeListener listener_size = (CompoundButton button, boolean isChecked) -> {
        if (button.getId() != x3.getId()) x3.setChecked(false);
        if (button.getId() != x4.getId()) x3.setChecked(false);
        if (button.getId() != x5.getId()) x3.setChecked(false);

        size = button.getText().toString().substring(1,1);
    };

    private CompoundButton.OnCheckedChangeListener listener_comp = (CompoundButton button, boolean isChecked) -> {
        if (button.getId() != x3.getId()) easy.setChecked(false);
        if (button.getId() != x4.getId()) medium.setChecked(false);
        if (button.getId() != x5.getId()) hard.setChecked(false);


    };

    private View.OnClickListener listener_go = (View v) -> {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("mode", result);
        startActivity(intent);
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        go = findViewById(R.id.go);

        go.setOnClickListener(listener_go);

        x3 = findViewById(R.id.x3);
        x4 = findViewById(R.id.x4);
        x5 = findViewById(R.id.x5);

        x3.setOnCheckedChangeListener(listener_size);
        x4.setOnCheckedChangeListener(listener_size);
        x5.setOnCheckedChangeListener(listener_size);

        easy = findViewById(R.id.easy);
        medium = findViewById(R.id.medium);
        hard = findViewById(R.id.hard);

        easy.setOnCheckedChangeListener(listener_comp);
        medium.setOnCheckedChangeListener(listener_comp);
        hard.setOnCheckedChangeListener(listener_comp);


    }
}
