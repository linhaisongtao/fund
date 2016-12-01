package com.example.daisongsong.chartapp.book;

import com.example.daisongsong.chartapp.book.data.FundManager;
import com.example.daisongsong.chartapp.book.model.BuyInfo;
import com.example.daisongsong.chartapp.book.model.CostInfo;
import com.example.daisongsong.chartapp.book.model.FundInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daisongsong on 2016/12/1.
 */

public class BookPresenter {
    private List<CostInfo> mCostInfos;
    private View mView;

    public BookPresenter(View view) {
        mView = view;
    }

    public void computeBooks() {
        List<FundInfo> fundInfos = FundManager.listBuyFunds();

        mCostInfos = new ArrayList<>();
        for (FundInfo buyFundInfo : fundInfos) {
            CostInfo info = new CostInfo();
            info.setFundInfo(buyFundInfo);
            info.setPrices(FundManager.getAllFundPrice(buyFundInfo.getFundCode()));
            info.setBuyInfos(FundManager.getBuyInfos(buyFundInfo.getFundCode()));
            mCostInfos.add(info);
        }

        compute();

        mView.showList(mCostInfos);
    }

    private void compute() {
        for (CostInfo costInfo : mCostInfos) {
            computeCost(costInfo);
        }
    }

    private void computeCost(CostInfo costInfo) {
        float totalMoney = 0f;
        for (BuyInfo info : costInfo.getBuyInfos()) {
            totalMoney += info.getMoney();
        }
        costInfo.setTotalMoney(totalMoney);
    }

    public interface View {
        void showList(List<CostInfo> infos);
    }
}
