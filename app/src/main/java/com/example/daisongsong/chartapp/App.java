package com.example.daisongsong.chartapp;

import android.app.Application;

/**
 * Created by daisongsong on 2016/11/28.
 */

public class App extends Application {
    private static App sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
    }

    public static App getApp() {
        return sApp;
    }
}
