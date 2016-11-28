package com.example.daisongsong.chartapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.daisongsong.chartapp.dataprocess.DataProcessActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        startActivity(new Intent(this, ChartActivity.class));
        startActivity(new Intent(this, DataProcessActivity.class));
    }
}
