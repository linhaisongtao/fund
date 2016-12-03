package com.example.daisongsong.chartapp.book.fundbuydetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.model.CostInfo;
import com.example.daisongsong.chartapp.book.model.DayCostMoneyInfo;
import com.example.daisongsong.chartapp.book.price.PriceListActivity;
import com.example.daisongsong.chartapp.book.widget.FundCostView;
import com.example.daisongsong.chartapp.chart.ChartActivity;
import com.example.daisongsong.chartapp.chart.view.ChartInfo;

import java.util.ArrayList;

/**
 * Created by daisongsong on 2016/12/1.
 */

public class FundBuyDetailActivity extends Activity {
    private CostInfo mCostInfo;

    private TextView mTextViewTitle;
    private ListView mListView;

    private FundBuyHistoryAdapter mAdapter;

    private FundCostView mFundCostView;

    public static void start(Context context, CostInfo costInfo) {
        Intent i = new Intent(context, FundBuyDetailActivity.class);
        i.putExtra("costInfo", costInfo);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_buy_detail);
        mCostInfo = (CostInfo) getIntent().getSerializableExtra("costInfo");

        initViews();
    }

    private void initViews() {
        mTextViewTitle = (TextView) findViewById(R.id.mTextViewTitle);
        mTextViewTitle.setText(mCostInfo.getFundInfo().getName() + "[" + mCostInfo.getFundInfo().getFundCode() + "]");

        mListView = (ListView) findViewById(R.id.mListView);
        mFundCostView = new FundCostView(this);
        mFundCostView.refreshView(mCostInfo);
        mFundCostView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PriceListActivity.start(v.getContext(), mCostInfo.getFundInfo().getFundCode());
            }
        });
        mListView.addHeaderView(mFundCostView);
        mAdapter = new FundBuyHistoryAdapter();
        mListView.setAdapter(mAdapter);

        mAdapter.setBuyInfos(mCostInfo.getBuyInfos());
        mAdapter.notifyDataSetChanged();

        findViewById(R.id.mTextViewChart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartActivity.start(v.getContext(), makeChartInfos());
            }
        });
    }

    private ChartInfo makeChartInfos() {
        ChartInfo info = new ChartInfo();

        float maxY = Float.MIN_VALUE;

        ArrayList<DayCostMoneyInfo> moneyInfos = mCostInfo.getMoneyInfos();
        String[] x = new String[moneyInfos.size()];
        ArrayList<Float> prices = new ArrayList<>();
        ArrayList<Float> averagePrices = new ArrayList<>();
        for (int i = 0; i < moneyInfos.size(); i++) {
            DayCostMoneyInfo moneyInfo = moneyInfos.get(i);
            x[i] = moneyInfo.getDate();

            float price = moneyInfo.getPrice();
            prices.add(price);

            float averagePrice;
            if (moneyInfo.getTotalCount() == 0) {
                averagePrice = moneyInfo.getPrice();
            } else {
                averagePrice = moneyInfo.getTotalMoney() / moneyInfo.getTotalCount();
            }
            averagePrices.add(averagePrice);

            maxY = Math.max(maxY, Math.max(price, averagePrice));
        }

        info.setX(x);
        info.addY(prices, "净值");
        info.addY(averagePrices, "成本");

        info.setMax(maxY);
        info.setMin(0f);
        info.setWidth(maxY - 0f);
        info.setNeedInit(false);

        return info;
    }

}
