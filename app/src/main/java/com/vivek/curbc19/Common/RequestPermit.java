package com.vivek.curbc19.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.vivek.curbc19.R;

public class RequestPermit extends AppCompatActivity {

    CheckedTextView tick1;
    CheckedTextView tick2;
    CheckedTextView tick3;
    CheckedTextView tick4;
    CheckedTextView tick5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_request_permit);

        tick1 = findViewById(R.id.view5);
        tick2 = findViewById(R.id.view6);
        tick3 = findViewById(R.id.view7);
        tick4 = findViewById(R.id.view8);
        tick5 = findViewById(R.id.view9);
    }

    public void checkBox1(View view) {
        if (tick1.isChecked()) {
            tick1.setChecked(false);
        } else {
            tick1.setChecked(true);
        }
    }

    public void checkBox2(View view) {
            if (tick2.isChecked()) {
                tick2.setChecked(false);
            }
            else {
                tick2.setChecked(true);
            }
        }

    public void checkBox3(View view) {
        if (tick3.isChecked()) {
            tick3.setChecked(false);
        }
        else {
            tick3.setChecked(true);
        }
    }

    public void checkBox4(View view) {
        if (tick4.isChecked()) {
            tick4.setChecked(false);
        }
        else {
            tick4.setChecked(true);
        }

    }

    public void checkBox5(View view) {

        if (tick5.isChecked()) {
            tick5.setChecked(false);
        }
        else {
            tick5.setChecked(true);
        }

    }

    public void submitRequest(View view) {

        Toast.makeText(this, "Request Submitted Successfully", Toast.LENGTH_LONG).show();
        finish();

    }


}
