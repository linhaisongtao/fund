package com.example.daisongsong.chartapp.util;

/**
 * Created by daisongsong on 2016/12/3.
 */

public class NumberUtil {

    public static String floatToString(float f) {
        return floatToString(f, 2);
    }

    public static String floatToString(float f, int precision) {
        String formatter = "%." + precision + "f";
        return String.format(formatter, f);
    }
}
