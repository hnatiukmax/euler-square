package com.masterschief.eulersquare.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.masterschief.eulersquare.R;
import com.masterschief.eulersquare.activities.MainActivity;
import com.masterschief.eulersquare.contracts.ChronometrContract;
import com.masterschief.eulersquare.contracts.GameContract;
import com.masterschief.eulersquare.logic.Mode;
import com.masterschief.eulersquare.logic.Pair;
import com.masterschief.eulersquare.presenters.GamePresenter;
import com.masterschief.eulersquare.contracts.DeskContract;
import com.masterschief.eulersquare.ui.Desk;
import com.masterschief.eulersquare.ui.MediaEffect;
import com.masterschief.eulersquare.utils.Preferences;
import com.masterschief.eulersquare.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameView extends AppCompatActivity implements GameContract.GameViewContract, View.OnClickListener {
    transient private final long CLICK_INTERVAL = 300;
    transient private long mLastClickTime;

    //UI user's components
    private List<ImageView> btnsNums;
    private List<ImageView> btnsChars;
    private ImageView btnHint;
    private ImageView btnBack;
    private ImageView btnSettings;
    private ImageView btnRestart;
    private ImageView btnNewGame;
    private ImageView btnPause;

    //UI game's components
    private ChronometrContract chronometer;
    private DeskContract gameDesk;

    private final long hintInSecond = 10000;
    private Long lastPause;
    private Pair currentCell;
    private Mode mode;
    private GameContract.GamePresenterContract presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);

        mode = (Mode) getIntent().getSerializableExtra("mode");

        initUI();
        attachPresenter();

        chronometer.onStart();
        if (savedInstanceState != null) {
            Utils.log("base is " + savedInstanceState.getLong("time"));
            chronometer.onHint( - 1 * savedInstanceState.getLong("time"));
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        gameDesk.setPause();
        chronometer.onPause();
    }

    private void initUI() {
        btnsChars = new ArrayList<>(5);
        btnsNums = new ArrayList<>(5);

        btnsChars.add(findViewById(R.id.buttonA));
        btnsChars.add(findViewById(R.id.buttonB));
        btnsChars.add(findViewById(R.id.buttonC));
        btnsChars.add(findViewById(R.id.buttonD));
        btnsChars.add(findViewById(R.id.buttonE));

        btnsNums.add(findViewById(R.id.button1));
        btnsNums.add(findViewById(R.id.button2));
        btnsNums.add(findViewById(R.id.button3));
        btnsNums.add(findViewById(R.id.button4));
        btnsNums.add(findViewById(R.id.button5));

        btnHint = findViewById(R.id.btn_hint);
        btnBack = findViewById(R.id.btn_back);
        btnSettings = findViewById(R.id.btn_settigns);
        btnRestart = findViewById(R.id.btn_restart);
        btnNewGame = findViewById(R.id.btn_newgame);
        btnPause = findViewById(R.id.btn_pause);
        chronometer = (ChronometrContract) findViewById(R.id.chronometer);

        btnHint.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnRestart.setOnClickListener(this);
        btnNewGame.setOnClickListener(this);
        btnPause.setOnClickListener(this);

        //set onClickMethods
        for (int i = 0; i < btnsChars.size(); i++) {
            btnsNums.get(i).setOnClickListener(this);
            btnsChars.get(i).setOnClickListener(this);
        }

        //turn off redutant buttons
        for (int i = 4; i > mode.size.getSize() - 1; i--) {
            btnsChars.get(i).setVisibility(View.INVISIBLE);
            btnsNums.get(i).setVisibility(View.INVISIBLE);
        }

        currentCell = new Pair(-1, -1);

        gameDesk = findViewById(R.id.viewDesk);
        ((View) gameDesk).setOnTouchListener(desk_listener);

    }

    /*
    /   lifecycle methods
    */

    private void attachPresenter() {
        presenter = (GameContract.GamePresenterContract) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            presenter = new GamePresenter(mode);
        }
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong("time", chronometer.getBase() - SystemClock.elapsedRealtime());
    }

    //onClick method implementation

    @Override
    public void onClick(View v) {

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.blink);
        v.startAnimation(anim);
        switch (v.getId()) {
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
                MediaEffect.getInstance().mPlay(this, MediaEffect.choice);
                if (gameDesk.getCurrentStateUpdate() != Desk.TypeUpdate.PAUSE && gameDesk.getCurrentStateUpdate() != Desk.TypeUpdate.WIN) {
                    presenter.onAction(
                            btnsNums.indexOf(v),
                            false
                    );
                }
                break;
            case R.id.buttonA:
            case R.id.buttonB:
            case R.id.buttonC:
            case R.id.buttonD:
            case R.id.buttonE:
                MediaEffect.getInstance().mPlay(this, MediaEffect.choice);
                if (gameDesk.getCurrentStateUpdate() != Desk.TypeUpdate.PAUSE && gameDesk.getCurrentStateUpdate() != Desk.TypeUpdate.WIN) {
                    presenter.onAction(
                            btnsChars.indexOf(v),
                            true
                    );
                }
                break;
            case R.id.btn_hint:
                MediaEffect.getInstance().mPlay(this, MediaEffect.hint);
                if (gameDesk.getCurrentStateUpdate() != Desk.TypeUpdate.PAUSE && gameDesk.getCurrentStateUpdate() != Desk.TypeUpdate.WIN) {
                    if (presenter.onHint()) {
                        if (gameDesk.getCurrentStateUpdate() != Desk.TypeUpdate.WIN)
                            chronometer.onHint(hintInSecond);
                    }
                }
                break;
            case R.id.btn_newgame:
                MediaEffect.getInstance().mPlay(this, MediaEffect.click);
                presenter.onNewGame();
                break;
            case R.id.btn_restart:
                MediaEffect.getInstance().mPlay(this, MediaEffect.click);
                presenter.onRestartGame();
                break;
            case R.id.btn_pause:
                MediaEffect.getInstance().mPlay(this, MediaEffect.click);
                presenter.onPauseGame();
                break;

            //buttons to other activities
            case R.id.btn_back:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btn_settigns:
                buildDialog().show();
                break;
        }
    }

    View.OnTouchListener desk_listener = (View view, MotionEvent event) -> {
        if (SystemClock.elapsedRealtime() - mLastClickTime < CLICK_INTERVAL) {
            return false;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        if (gameDesk.getCurrentStateUpdate() == Desk.TypeUpdate.PAUSE) {
            chronometer.onStart();
            gameDesk.updateDesk();
            return true;
        }

        int x = (int) (event.getY() / (view.getWidth() / mode.size.getSize()));
        int y = (int) (event.getX() / (view.getWidth() / mode.size.getSize()));

        if (currentCell.equals(new Pair(x, y))) {
            currentCell.first = -1;
            currentCell.second = -1;
        } else {
            currentCell.first = x;
            currentCell.second = y;
        }

        gameDesk.updateDesk();
        return true;
    };


    //GameViewContract methods

    @Override
    public void updateDesk() {
        gameDesk.updateDesk();
    }

    @Override
    public void setWinDeskState() {
        chronometer.onHint(hintInSecond);
        gameDesk.setWin(chronometer.getTime());
        chronometer.setWin();
    }

    @Override
    public void setPause() {
        if (gameDesk.getCurrentStateUpdate() != Desk.TypeUpdate.WIN) {
            gameDesk.setPause();
            chronometer.onPause();
        }
    }

    @Override
    public void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 50);
        toast.show();
    }

    @Override
    public Pair getCurrentCell() {
        return currentCell;
    }

    @Override
    public void setSource(Pair[][] desk) {
        gameDesk.initDesk(desk, currentCell, mode.size.getSize());
    }

    // dialog
    public AlertDialog buildDialog() {
        Preferences settings = new Preferences(this);
        AtomicBoolean sound = new AtomicBoolean(settings.getSound());
        AtomicBoolean vibro = new AtomicBoolean(settings.getVibro());
        Utils.log("SV before OK is | sound " + sound.get() + " vibro " + vibro.get());
        final boolean[] checks = {sound.get(), vibro.get()};
        final String[] names = {"Sound", "Vibration"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Settings")
                .setCancelable(false)
                .setMultiChoiceItems(names, checks,
                        (DialogInterface dialog, int which, boolean isChecked) -> {
                            if (which == 0) {
                                sound.set(isChecked);
                            } else if (which == 1) {
                                vibro.set(isChecked);
                            }
                        }
                )
                .setPositiveButton("OK",
                        (DialogInterface dialog, int id) -> {
                            Utils.log("SV after OK is | sound " + sound.get() + " vibro " + vibro.get());
                            settings.setSound(sound.get());
                            settings.setVibro(vibro.get());
                        }
                )
                .setNegativeButton("Cancel",
                        (DialogInterface dialog, int id) -> {
                            dialog.cancel();
                        });
        return builder.create();
    }
}
