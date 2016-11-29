package com.example.daisongsong.chartapp.dataprocess;

/**
 * Created by daisongsong on 2016/11/28.
 */

public class DayData extends FundData {
    private float mMoney;
    private float mTotalMoney;
    private float mCount;
    private float mTotalCount;
    private float mMarketMoney;
    private float mRatio;
    private float mUnitCost;

    private float mPriceRatio;

    private boolean mInProcess = false;

    public DayData(FundData data) {
        setDate(data.getDate());
        setPrice(data.getPrice());
    }

    public float getMoney() {
        return mMoney;
    }

    public void setMoney(float money) {
        mMoney = money;
    }

    public float getTotalMoney() {
        return mTotalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        mTotalMoney = totalMoney;
    }

    public float getCount() {
        return mCount;
    }

    public void setCount(float count) {
        mCount = count;
    }

    public float getTotalCount() {
        return mTotalCount;
    }

    public void setTotalCount(float totalCount) {
        mTotalCount = totalCount;
    }

    public float getMarketMoney() {
        return mMarketMoney;
    }

    public void setMarketMoney(float marketMoney) {
        mMarketMoney = marketMoney;
    }

    public float getRatio() {
        return mRatio;
    }

    public void setRatio(float ratio) {
        mRatio = ratio;
    }

    public float getUnitCost() {
        return mUnitCost;
    }

    public void setUnitCost(float unitCost) {
        mUnitCost = unitCost;
    }

    public boolean isInProcess() {
        return mInProcess;
    }

    public void setInProcess(boolean inProcess) {
        mInProcess = inProcess;
    }

    public float getPriceRatio() {
        return mPriceRatio;
    }

    public void setPriceRatio(float priceRatio) {
        mPriceRatio = priceRatio;
    }
}
