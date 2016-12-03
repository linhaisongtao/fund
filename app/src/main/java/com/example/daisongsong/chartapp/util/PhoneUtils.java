package com.example.daisongsong.chartapp.util;

import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.example.daisongsong.chartapp.App;

/**
 * Created by daisongsong on 2016/12/3.
 */

public class PhoneUtils {

    public static Rect getScreenSize() {
        DisplayMetrics metrics = App.getApp().getResources().getDisplayMetrics();
        Rect rect = new Rect(0, 0, metrics.widthPixels, metrics.heightPixels);
        return rect;
    }
}
