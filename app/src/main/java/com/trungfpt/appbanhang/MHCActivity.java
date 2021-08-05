package com.trungfpt.appbanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.trungfpt.appbanhang.activity.MainActivity;

public class MHCActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mhcactivity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MHCActivity.this, DangNhapActivity.class);
                startActivity(intent);
            }
        },4000);
    }
}