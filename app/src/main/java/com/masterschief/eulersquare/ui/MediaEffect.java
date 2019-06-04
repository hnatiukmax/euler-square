/**
 * The HelloWorld program implements an application that
 * simply displays "Hello World!" to the standard output.
 *
 * @author  Maxim Hnatiuk
 * @version 1.0
 * @since   2019-03-28
 */

package com.masterschief.eulersquare.ui;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Gravity;
import android.widget.Toast;

import com.masterschief.eulersquare.R;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Класс, с методами мультимедии
 */


public class MediaEffect {
    private static MediaEffect instance;
    private boolean statePlayer;
    private boolean stateVibro;

    public static final int click = R.raw.click;

    private MediaEffect() {
        statePlayer = stateVibro = true;
    }

    public static MediaEffect getInstance() {
        if (instance == null) {
            instance = new MediaEffect();
        }
        return instance;
    }

    public boolean setStatePlayer() {
        return statePlayer;
    }

    public boolean setStateVibro() {
        return stateVibro;
    }

    //вибрация
    public void vibro(Context context, int msec) {
        if (stateVibro) {
            if (Build.VERSION.SDK_INT >= 26) {
                ((Vibrator) context.getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(msec / 3, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                ((Vibrator) context.getSystemService(VIBRATOR_SERVICE)).vibrate(msec);
            }
        }
    }

    public void mPlay(Context context, int res) {
        if (statePlayer) {
            try {
                MediaPlayer mMediaPlayer = MediaPlayer.create(context, res);
                mMediaPlayer.start();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    //всплывающие сообщения
    public static void showToast(Context context, String text) {
        Toast toast;
        toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0 , 25);
        toast.show();
    }
}
