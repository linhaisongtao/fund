package com.example.daisongsong.chartapp.book.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daisongsong on 2016/12/1.
 */

public class CostInfo implements Serializable {
    private FundInfo mFundInfo;
    private List<DayCostMoneyInfo> mMoneyInfos;
    private List<BuyInfo> mBuyInfos;

    public FundInfo getFundInfo() {
        return mFundInfo;
    }

    public void setFundInfo(FundInfo fundInfo) {
        mFundInfo = fundInfo;
    }


    public List<BuyInfo> getBuyInfos() {
        return mBuyInfos;
    }

    public void setBuyInfos(List<BuyInfo> buyInfos) {
        mBuyInfos = buyInfos;
    }


    public List<DayCostMoneyInfo> getMoneyInfos() {
        return mMoneyInfos;
    }

    public void setMoneyInfos(List<DayCostMoneyInfo> moneyInfos) {
        mMoneyInfos = moneyInfos;
    }

    public void setMoneyInfosWithFundPrices(List<FundPrice> prices) {
        mMoneyInfos = new ArrayList<>();
        for (int i = 0; i < prices.size(); i++) {
            mMoneyInfos.add(new DayCostMoneyInfo(prices.get(i)));
        }
    }

    public DayCostMoneyInfo getCurrentMoneyInfo() {
        return mMoneyInfos.get(mMoneyInfos.size() - 1);
    }
}
