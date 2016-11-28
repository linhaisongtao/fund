package com.example.daisongsong.chartapp.dataprocess;

import java.io.Serializable;

/**
 * Created by daisongsong on 2016/11/28.
 */

public class FundData implements Serializable {
    private String mDate;
    private float mPrice;

    public FundData() {
    }

    public FundData(String date, float price) {
        mDate = date;
        mPrice = price;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        mPrice = price;
    }
}
