package com.example.daisongsong.chartapp.book.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by daisongsong on 2016/12/1.
 */

public class CostInfo implements Serializable {
    private FundInfo mFundInfo;
    private List<FundInfo.FundPrice> mPrices;
    private List<BuyInfo> mBuyInfos;

    private float mTotalMoney;

    public void setFundInfo(FundInfo fundInfo) {
        mFundInfo = fundInfo;
    }

    public void setPrices(List<FundInfo.FundPrice> prices) {
        mPrices = prices;
    }

    public void setBuyInfos(List<BuyInfo> buyInfos) {
        mBuyInfos = buyInfos;
    }

    public FundInfo getFundInfo() {
        return mFundInfo;
    }

    public List<FundInfo.FundPrice> getPrices() {
        return mPrices;
    }

    public List<BuyInfo> getBuyInfos() {
        return mBuyInfos;
    }

    public void setTotalMoney(float totalMoney) {
        mTotalMoney = totalMoney;
    }

    public float getTotalMoney() {
        return mTotalMoney;
    }
}
