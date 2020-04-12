package com.vivek.curbc19.HelperClasses.HomeAdapter;

import android.widget.ImageView;
import android.widget.TextView;

public class MostViewedHelperClass {
    int imageView;
    String textView;

    public MostViewedHelperClass(int imageView, String textView) {
        this.imageView = imageView;
        this.textView = textView;
    }

    public int getImageView() {
        return imageView;
    }

    public String getTextView() {
        return textView;
    }

}

