package com.vivek.curbc19.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.vivek.curbc19.R;

public class ReportViolation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reportviolation);
    }

    public void report_violation(View view) {

        Toast.makeText(this, "Report Submitted Successfully", Toast.LENGTH_LONG).show();
        finish();

    }
}
