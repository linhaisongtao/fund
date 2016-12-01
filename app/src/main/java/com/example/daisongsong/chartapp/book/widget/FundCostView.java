package com.example.daisongsong.chartapp.book.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.model.CostInfo;
import com.example.daisongsong.chartapp.book.model.DayCostMoneyInfo;

/**
 * Created by daisongsong on 2016/12/1.
 */

public class FundCostView extends FrameLayout {
    private TextView mTextViewName;
    private TextView mTextViewTotalMoney;
    private TextView mTextViewMarketMoney;
    private TextView mTextViewTotalCount;
    private TextView mTextViewTotalMoneyRatio;

    public FundCostView(Context context) {
        super(context);
        init();
    }

    public FundCostView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FundCostView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_cost, this);

        mTextViewName = (TextView) findViewById(R.id.mTextViewName);
        mTextViewTotalMoney = (TextView) findViewById(R.id.mTextViewTotalMoney);
        mTextViewMarketMoney = (TextView) findViewById(R.id.mTextViewMarketMoney);
        mTextViewTotalCount = (TextView) findViewById(R.id.mTextViewTotalCount);
        mTextViewTotalMoneyRatio = (TextView) findViewById(R.id.mTextViewTotalMoneyRatio);
    }

    public void refreshView(CostInfo costInfo) {
        mTextViewName.setText(costInfo.getFundInfo().getName() + "[" + costInfo.getFundInfo().getFundCode() + "]");
        DayCostMoneyInfo moneyInfo = costInfo.getCurrentMoneyInfo();
        mTextViewTotalMoney.setText("本金:" + moneyInfo.getTotalMoney());
        mTextViewMarketMoney.setText("市值:" + moneyInfo.getTotalMarketMoney());
        mTextViewTotalCount.setText("份额:" + moneyInfo.getTotalCount());

        float moneyRatio = (moneyInfo.getTotalMarketMoney() - moneyInfo.getTotalMoney()) / moneyInfo.getTotalMoney();
        String rationString = String.format("%.2f", moneyRatio * 100);
        mTextViewTotalMoneyRatio.setText("(" + rationString + "%" + ")");
        if (moneyRatio >= 0f) {
            mTextViewTotalMoneyRatio.setTextColor(Color.RED);
        } else {
            mTextViewTotalMoneyRatio.setTextColor(Color.GREEN);
        }
    }
}
