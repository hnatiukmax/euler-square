package com.masterschief.eulersquare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.masterschief.eulersquare.R;

public class MainActivity extends AppCompatActivity {

    private View.OnClickListener listener_go = (View v) -> {
        Intent intent = new Intent(this, GameActivity.class);
        //intent.putExtra("mode", result);
        startActivity(intent);
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
