package com.example.daisongsong.chartapp.chart.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daisongsong on 2016/11/28.
 */

public class ChartInfo implements Serializable {
    private String[] mX;

    private ArrayList<ArrayList<Float>> mY;
    private float mMin;
    private float mMax;
    private float mWidth;

    public static ChartInfo test() {
        ChartInfo info = new ChartInfo();
        String[] x = new String[]{"1", "2", "3", "4"};
        info.mX = x;

        info.mY = new ArrayList<>();
        ArrayList<Float> list = new ArrayList<>();
        list.add(1f);
        list.add(2f);
        list.add(3f);
        list.add(6f);
        info.mY.add(list);

        ArrayList<Float> list1 = new ArrayList<>();
        list1.add(3f);
        list1.add(1f);
        list1.add(8f);
        list1.add(1f);
        info.mY.add(list1);

        return info;
    }

    public ArrayList<ArrayList<Float>> getY() {
        return mY;
    }

    public void setY(ArrayList<ArrayList<Float>> y) {
        mY = y;
    }

    public String[] getX() {
        return mX;
    }

    public void setX(String[] x) {
        mX = x;
    }

    public ArrayList<Float> getY(int d) {
        return mY.get(d);
    }

    public int dSize() {
        return mY.size();
    }

    public float width() {
        return mWidth <= 0 ? 1 : mWidth;
    }

    public float getMax() {
        return mMax;
    }

    public float getMin() {
        return mMin;
    }

    public void init() {
        mMax = Float.MIN_VALUE;
        mMin = Float.MAX_VALUE;
        for (List<Float> floats : mY) {
            for (Float aFloat : floats) {
                if (aFloat > mMax) {
                    mMax = aFloat;
                }
                if (aFloat < mMin) {
                    mMin = aFloat;
                }
            }
        }
        mWidth = mMax - mMin;
    }
}
