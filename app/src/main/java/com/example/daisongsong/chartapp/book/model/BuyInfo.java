package com.example.daisongsong.chartapp.book.model;

import java.io.Serializable;

/**
 * Created by daisongsong on 2016/12/1.
 */

public class BuyInfo implements Serializable {
    private FundInfo.FundPrice mFundPrice;
    private long mFundTime;
    private float mMoney;

    public FundInfo.FundPrice getFundPrice() {
        return mFundPrice;
    }

    public void setFundPrice(FundInfo.FundPrice fundPrice) {
        mFundPrice = fundPrice;
    }

    public float getMoney() {
        return mMoney;
    }

    public void setMoney(float money) {
        mMoney = money;
    }

    public long getFundTime() {
        return mFundTime;
    }

    public void setFundTime(long fundTime) {
        mFundTime = fundTime;
    }
}
