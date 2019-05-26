package com.masterschief.eulersquare.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.masterschief.eulersquare.R;
import com.masterschief.eulersquare.controller.GameController;
import com.masterschief.eulersquare.logic.Mode;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private ArrayList<ImageView> buttonAlp;
    private ArrayList<ImageView> buttonNum;
    private ImageView hint;
    private ImageView back;
    private ImageView settings;
    private ImageView restart;
    private ImageView newGame;

    private View.OnClickListener listener_navigation = (View v) -> {
        Animation anim;
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btn_back:
                anim = AnimationUtils.loadAnimation(this, R.anim.blink);
                v.startAnimation(anim);
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_settigns:
                anim = AnimationUtils.loadAnimation(this, R.anim.rotate);
                v.startAnimation(anim);
                //intent = new Intent(this, Settings.class);
                //startActivity(intent);
                break;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //get info about game
        Intent intent = getIntent();
        Mode mode = (Mode) (intent.getSerializableExtra("mode"));

        buttonAlp = new ArrayList<>(5);
        buttonNum = new ArrayList<>(5);

        buttonAlp.add(findViewById(R.id.buttonA));
        buttonAlp.add(findViewById(R.id.buttonB));
        buttonAlp.add(findViewById(R.id.buttonC));
        buttonAlp.add(findViewById(R.id.buttonD));
        buttonAlp.add(findViewById(R.id.buttonE));

        buttonNum.add(findViewById(R.id.button1));
        buttonNum.add(findViewById(R.id.button2));
        buttonNum.add(findViewById(R.id.button3));
        buttonNum.add(findViewById(R.id.button4));
        buttonNum.add(findViewById(R.id.button5));

        hint = findViewById(R.id.btn_hint);
        back = findViewById(R.id.btn_back);
        settings = findViewById(R.id.btn_settigns);
        restart = findViewById(R.id.btn_restart);
        newGame = findViewById(R.id.btn_newgame);

        settings.setOnClickListener(listener_navigation);
        back.setOnClickListener(listener_navigation);

        for (int i = 4; i > mode.size.getSize() - 1; i--) {
            buttonAlp.get(i).setVisibility(View.INVISIBLE);
            buttonNum.get(i).setVisibility(View.INVISIBLE);
        }


        //start game
        GameController controller = new GameController(this, buttonAlp, buttonNum, hint, restart, newGame, findViewById(R.id.viewDesk), mode);
        controller.start();
    }
}
