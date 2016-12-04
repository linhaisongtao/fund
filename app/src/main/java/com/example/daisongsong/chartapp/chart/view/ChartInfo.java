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
    private ArrayList<String> mYNames;
    private float mMin = Float.MIN_VALUE;
    private float mMax = Float.MIN_VALUE;
    private float mWidth = Float.MIN_VALUE;

    private boolean mNeedInit = true;

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
        if (mY != null) {
            mYNames = new ArrayList<>(mY.size());
            for (int i = 0; i < mY.size(); i++) {
                mYNames.add("y" + (i + 1));
            }
        }
    }

    public void addY(ArrayList<Float> y, String yName) {
        if (mY == null) {
            mY = new ArrayList<>();
            mYNames = new ArrayList<>();
        }
        mY.add(y);
        mYNames.add(yName);

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

    public void setMax(float max) {
        mMax = max;
    }

    public float getMin() {
        return mMin;
    }

    public void setMin(float min) {
        mMin = min;
    }

    public void setWidth(float width) {
        mWidth = width;
    }

    public void init() {
        if (!mNeedInit) {
            return;
        }
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

    public boolean isNeedInit() {
        return mNeedInit;
    }

    public void setNeedInit(boolean needInit) {
        mNeedInit = needInit;
    }

    public ArrayList<String> getYNames() {
        return mYNames;
    }

}
