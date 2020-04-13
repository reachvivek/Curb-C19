package com.vivek.curbc19.Common;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.vivek.curbc19.HelperClasses.HelplineAdapter.HelplineAdapter;
import com.vivek.curbc19.HelperClasses.HelplineAdapter.HelplineItem;
import com.vivek.curbc19.R;

import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HelplineActivity extends AppCompatActivity implements HelplineAdapter.HelplineAdapterEvents {

    RecyclerView NewsRecyclerview;
    HelplineAdapter newsAdapter;
    List<HelplineItem> mData;
    FloatingActionButton fabSwitcher;
    boolean isDark = false;
    ConstraintLayout rootLayout;
    EditText searchInput;
    CharSequence search = "";
    private static final int REQUEST_PHONE_CALL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // let's make this activity on full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_faqs);


        //Exp
        setPermission();

        // hide the action bar
        getSupportActionBar().hide();


        // ini view

        fabSwitcher = findViewById(R.id.fab_switcher);
        rootLayout = findViewById(R.id.root_layout);
        searchInput = findViewById(R.id.search_input);
        NewsRecyclerview = findViewById(R.id.news_rv);
        mData = new ArrayList<>();

        // load theme state

        isDark = getThemeStatePref();
        if (isDark) {
            // dark theme is on

            searchInput.setBackgroundResource(R.drawable.search_input_dark_style);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.black));

        } else {
            // light theme is on
            searchInput.setBackgroundResource(R.drawable.search_input_style);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.white));

        }


        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        // fill list news with data
        // just for testing purpose i will fill the news list with random data
        // you may get your data from an api / firebase or sqlite database ...

        //States
        mData.add(new HelplineItem("Andhra Pradesh", "0866-2410978", R.drawable.one, R.drawable.call));

        mData.add(new HelplineItem("Arunachal Pradesh", "9436055743", R.drawable.two, R.drawable.call));

        mData.add(new HelplineItem("Assam", "6913347770", R.drawable.three, R.drawable.call));

        mData.add(new HelplineItem("Bihar", "104", R.drawable.four, R.drawable.call));

        mData.add(new HelplineItem("Chhattisgarh", "104", R.drawable.five, R.drawable.call));

        mData.add(new HelplineItem("Goa", "104", R.drawable.six, R.drawable.call));

        mData.add(new HelplineItem("Gujarat", "104", R.drawable.seven, R.drawable.call));

        mData.add(new HelplineItem("Haryana", "8558893911", R.drawable.eight, R.drawable.call));

        mData.add(new HelplineItem("Himachal Pradesh", "104", R.drawable.nine, R.drawable.call));

        mData.add(new HelplineItem("Jharkhand", "104", R.drawable.ten, R.drawable.call));

        mData.add(new HelplineItem("Karnataka", "104", R.drawable.eleven, R.drawable.call));

        mData.add(new HelplineItem("Kerala", "0471-2552056", R.drawable.twelve, R.drawable.call));

        mData.add(new HelplineItem("Madhya Pradesh", "104", R.drawable.thirteen, R.drawable.call));

        mData.add(new HelplineItem("Maharashtra", "020-26127394", R.drawable.fourteen, R.drawable.call));

        mData.add(new HelplineItem("Manipur", "3852411668", R.drawable.fifteen, R.drawable.call));

        mData.add(new HelplineItem("Meghalaya", "108", R.drawable.sixteen, R.drawable.call));

        mData.add(new HelplineItem("Mizoram", "102", R.drawable.seventeen, R.drawable.call));

        mData.add(new HelplineItem("Nagaland", "7005539653", R.drawable.eighteen, R.drawable.call));

        mData.add(new HelplineItem("Odisha", "9439994859", R.drawable.nineteen, R.drawable.call));

        mData.add(new HelplineItem("Punjab", "104", R.drawable.twenty, R.drawable.call));

        mData.add(new HelplineItem("Rajasthan", "0141-2225624", R.drawable.twentyone, R.drawable.call));

        mData.add(new HelplineItem("Sikkim", "104", R.drawable.twentytwo, R.drawable.call));

        mData.add(new HelplineItem("Tamil Nadu", "044-29510500", R.drawable.twentythree, R.drawable.call));

        mData.add(new HelplineItem("Telangana", "104", R.drawable.twentyfour, R.drawable.call));

        mData.add(new HelplineItem("Tripura", "0381-2315879", R.drawable.twentyfive, R.drawable.call));

        mData.add(new HelplineItem("Uttarakhand", "104", R.drawable.twentysix, R.drawable.call));

        mData.add(new HelplineItem("Uttar Pradesh", "18001805145", R.drawable.twentyseven, R.drawable.call));

        mData.add(new HelplineItem("West Bengal", "1800313444222", R.drawable.twentyeight, R.drawable.call));

        //Union Territories
        mData.add(new HelplineItem("Andaman and Nicobar Islands", "03192-232102", R.drawable.g1, R.drawable.call));

        mData.add(new HelplineItem("Chandigarh", "9779558282", R.drawable.g2, R.drawable.call));

        mData.add(new HelplineItem("Dadra and Nagar Haveli and Daman & Diu", "104", R.drawable.g3, R.drawable.call));

        mData.add(new HelplineItem("Delhi", "011-22307145", R.drawable.g4, R.drawable.call));

        mData.add(new HelplineItem("Jammu & Kashmir", "01912520982", R.drawable.g5, R.drawable.call));

        mData.add(new HelplineItem("Ladakh", "01982256462", R.drawable.g6, R.drawable.call));

        mData.add(new HelplineItem("Lakshadweep", "104", R.drawable.g7, R.drawable.call));

        mData.add(new HelplineItem("Puducherry", "104", R.drawable.g8, R.drawable.call));


        // adapter ini and setup
        newsAdapter = new HelplineAdapter(this, mData, isDark);
        NewsRecyclerview.setAdapter(newsAdapter);
        NewsRecyclerview.setLayoutManager(new LinearLayoutManager(this));


        fabSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDark = !isDark;
                if (isDark) {

                    rootLayout.setBackgroundColor(getResources().getColor(R.color.black));
                    searchInput.setBackgroundResource(R.drawable.search_input_dark_style);

                } else {
                    rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    searchInput.setBackgroundResource(R.drawable.search_input_style);
                }

                newsAdapter = new HelplineAdapter(getApplicationContext(), mData, isDark);
                if (!search.toString().isEmpty()) {

                    newsAdapter.getFilter().filter(search);

                }
                NewsRecyclerview.setAdapter(newsAdapter);
                saveThemeStatePref(isDark);

            }
        });


        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                newsAdapter.getFilter().filter(s);
                search = s;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }



    private void saveThemeStatePref(boolean isDark) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isDark", isDark);
        editor.commit();
    }

    private boolean getThemeStatePref() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        boolean isDark = pref.getBoolean("isDark", false);
        return isDark;

    }

    private void setPermission() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(HelplineActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(HelplineActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
            }
            else
            {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_LONG).show();
                    finish();
                }
                return;
            }
        }
    }

    @Override
    public void onNumberClicked(HelplineItem helplineItem) {

    }

}
