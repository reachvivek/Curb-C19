package com.vivek.curbc19.HelperClasses.HomeAdapter;

import android.widget.ImageView;
import android.widget.TextView;

public class MostViewedHelperClass {
    int imageView;
    String textView;
    String news;

    public MostViewedHelperClass(int imageView, String textView, String news) {
        this.imageView = imageView;
        this.textView = textView;
        this.news = news;
    }

    public int getImageView() {
        return imageView;
    }

    public String getTextView() {
        return textView;
    }

    public String getNews() {
        return  news;
    }

}

