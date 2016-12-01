package com.example.daisongsong.chartapp.book.model;

import java.io.Serializable;

/**
 * Created by daisongsong on 2016/12/1.
 */
public class FundPrice implements Serializable {
    private FundInfo mFundInfo;

    private long mTime;
    private String mDate;
    private float mPrice;

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
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

    public FundInfo getFundInfo() {
        return mFundInfo;
    }

    public void setFundInfo(FundInfo fundInfo) {
        mFundInfo = fundInfo;
    }
}
