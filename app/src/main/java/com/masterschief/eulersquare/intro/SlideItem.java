package com.masterschief.eulersquare.intro;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.masterschief.eulersquare.R;

public class SlideItem extends Fragment{
    private int xmlRes;
    public static int firstSlide = R.layout.firstslide;
    public static int mistakesSlide = R.layout.secondslide;
    public static int finalSlide = R.layout.finalslide;


    public void setXmlRes(int xmlRes) {
        this.xmlRes = xmlRes;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(xmlRes, null);
    }
}
