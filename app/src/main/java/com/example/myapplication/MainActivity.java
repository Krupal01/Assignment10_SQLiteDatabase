package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03A9F4")));
        getWindow().setStatusBarColor(Color.parseColor("#026C9C"));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activityMain,new StudentListFragment())
                .commit();

    }
}