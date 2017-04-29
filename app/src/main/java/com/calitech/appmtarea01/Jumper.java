package com.calitech.appmtarea01;

import android.widget.ImageView;

/**
 * Created by jquin on 28-04-2017.
 */

public class Jumper {

    ImageView imageView;
    String animal;
    int position;

    public Jumper(ImageView iv, int index, String animal){
        this.imageView = iv;
        this.animal = animal;
        this.position = index;
    }

    public void Paint( int color){
        imageView.setColorFilter(color);
    }
}
