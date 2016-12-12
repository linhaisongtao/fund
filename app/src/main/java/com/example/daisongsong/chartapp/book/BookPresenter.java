package com.example.daisongsong.chartapp.book;

import com.example.daisongsong.chartapp.book.data.FundManager;
import com.example.daisongsong.chartapp.book.model.BuyInfo;
import com.example.daisongsong.chartapp.book.model.CostInfo;
import com.example.daisongsong.chartapp.book.model.DayCostMoneyInfo;
import com.example.daisongsong.chartapp.book.model.FundInfo;
import com.example.daisongsong.chartapp.book.model.FundPrice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daisongsong on 2016/12/1.
 */

public class BookPresenter {
    private List<CostInfo> mCostInfos;
    private View mView;

    private float mTotalMoney;
    private float mTotalMarketMoney;

    public BookPresenter(View view) {
        mView = view;
    }

    public void computeBooks() {
        List<FundInfo> fundInfos = FundManager.listBuyFunds();

        mCostInfos = new ArrayList<>();
        for (FundInfo buyFundInfo : fundInfos) {
            CostInfo info = new CostInfo();
            info.setFundInfo(buyFundInfo);
            info.setMoneyInfosWithFundPrices(FundManager.getAllFundPrice(buyFundInfo.getFundCode()));
            info.setBuyInfos((ArrayList<BuyInfo>) FundManager.getBuyInfos(buyFundInfo.getFundCode()));
            mCostInfos.add(info);
        }

        compute();

        mView.showList(mCostInfos);
        mView.showTotalInfo(mTotalMoney, mTotalMarketMoney);

    }

    private void compute() {
        mTotalMarketMoney = 0f;
        mTotalMoney = 0f;

        for (CostInfo costInfo : mCostInfos) {
            computeCost(costInfo);
            mTotalMoney += costInfo.getCurrentMoneyInfo().getTotalMoney();
            mTotalMarketMoney += costInfo.getCurrentMoneyInfo().getTotalMarketMoney();
        }
    }

    private void computeCost(CostInfo costInfo) {
        List<DayCostMoneyInfo> moneyInfos = costInfo.getMoneyInfos();
        for (int i = 0; i < moneyInfos.size(); i++) {
            DayCostMoneyInfo current = moneyInfos.get(i);
            DayCostMoneyInfo pre = i > 0 ? moneyInfos.get(i - 1) : null;
            processCostMoneys(costInfo, current, pre);
        }
    }

    private void processCostMoneys(CostInfo costInfo, DayCostMoneyInfo current, DayCostMoneyInfo pre) {
        float dayCount = 0f;
        float dayMoney = 0f;

        List<BuyInfo> buyInfos = findBuyInfos(costInfo, current);
        for (int i = 0; i < buyInfos.size(); i++) {
            BuyInfo buyInfo = buyInfos.get(i);
            dayMoney += buyInfo.getMoney();
            dayCount += buyInfo.getCount();
        }

        if (pre == null) {
            current.setTodayCount(dayCount);
            current.setTotalCount(dayCount);
            current.setTodayMoney(dayMoney);
            current.setTotalMoney(dayMoney);
            current.setTotalMarketMoney(current.getTotalCount() * current.getPrice());
        } else {
            current.setTodayCount(dayCount);
            current.setTodayMoney(dayMoney);
            current.setTotalCount(pre.getTotalCount() + current.getTodayCount());
            current.setTotalMoney(pre.getTotalMoney() + current.getTodayMoney());
            current.setTotalMarketMoney(current.getTotalCount() * current.getPrice());
        }
    }

    private List<BuyInfo> findBuyInfos(CostInfo costInfo, FundPrice price) {
        List<BuyInfo> buyInfos = new ArrayList<>();
        List<BuyInfo> buyInfoList = costInfo.getBuyInfos();
        for (int i = 0; i < buyInfoList.size(); i++) {
            if (buyInfoList.get(i).getFundPrice().getTime() == price.getTime()) {
                buyInfos.add(buyInfoList.get(i));
            }
        }
        return buyInfos;
    }

    public interface View {
        void showList(List<CostInfo> infos);

        void showTotalInfo(float totalMoney, float totalMarketMoney);
    }
}
