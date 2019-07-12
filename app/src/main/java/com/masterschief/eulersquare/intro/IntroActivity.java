package com.masterschief.eulersquare.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.masterschief.eulersquare.activities.MainActivity;

public class IntroActivity extends AppIntro2 {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFadeAnimation();
        SlideItem firstItem = new SlideItem();
        firstItem.setXmlRes(SlideItem.firstSlide);

        SlideItem secondItem = new SlideItem();
        secondItem.setXmlRes(SlideItem.mistakesSlide);

        SlideItem finalItem = new SlideItem();
        finalItem.setXmlRes(SlideItem.finalSlide);

        addSlide(firstItem);
        addSlide(secondItem);
        addSlide(finalItem);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        startActivity(new Intent(this, MainActivity.class));
    }
}
