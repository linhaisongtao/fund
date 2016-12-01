package com.example.daisongsong.chartapp.book.model;

/**
 * Created by daisongsong on 2016/12/1.
 */

public class DayCostMoneyInfo extends FundPrice {
    private float mTotalMoney;
    private float mTotalMarketMoney;
    private float mTotalCount;
    private float mTodayMoney;
    private float mTodayCount;

    public DayCostMoneyInfo(FundPrice price) {
        this.setTime(price.getTime());
        this.setFundInfo(price.getFundInfo());
        this.setDate(price.getDate());
        this.setPrice(price.getPrice());
    }

    public float getTotalMoney() {
        return mTotalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        mTotalMoney = totalMoney;
    }

    public float getTotalMarketMoney() {
        return mTotalMarketMoney;
    }

    public void setTotalMarketMoney(float totalMarketMoney) {
        mTotalMarketMoney = totalMarketMoney;
    }

    public float getTotalCount() {
        return mTotalCount;
    }

    public void setTotalCount(float totalCount) {
        mTotalCount = totalCount;
    }

    public float getTodayMoney() {
        return mTodayMoney;
    }

    public void setTodayMoney(float todayMoney) {
        mTodayMoney = todayMoney;
    }

    public float getTodayCount() {
        return mTodayCount;
    }

    public void setTodayCount(float todayCount) {
        mTodayCount = todayCount;
    }
}
