package com.example.daisongsong.chartapp.book.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.model.CostInfo;
import com.example.daisongsong.chartapp.book.model.DayCostMoneyInfo;
import com.example.daisongsong.chartapp.util.NumberUtil;

/**
 * Created by daisongsong on 2016/12/1.
 */

public class FundCostView extends FrameLayout {
    private TextView mTextViewName;

    private PriceItemView mPriceItemViewCount;
    private PriceItemView mPriceItemViewMarketPrice;
    private PriceItemView mPriceItemViewLibProfit;
    private PriceItemView mPriceItemViewTotalMoney;
    private PriceItemView mPriceItemViewUnitCost;
    private PriceItemView mPriceItemViewCurrentPrice;

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

        mPriceItemViewCount = (PriceItemView) findViewById(R.id.mPriceItemViewCount);
        mPriceItemViewMarketPrice = (PriceItemView) findViewById(R.id.mPriceItemViewMarketPrice);
        mPriceItemViewLibProfit = (PriceItemView) findViewById(R.id.mPriceItemViewLibProfit);
        mPriceItemViewTotalMoney = (PriceItemView) findViewById(R.id.mPriceItemViewTotalMoney);
        mPriceItemViewUnitCost = (PriceItemView) findViewById(R.id.mPriceItemViewUnitCost);
        mPriceItemViewCurrentPrice = (PriceItemView) findViewById(R.id.mPriceItemViewCurrentPrice);
    }

    public void refreshView(CostInfo costInfo) {
        mTextViewName.setText(costInfo.getFundInfo().getName() + "[" + costInfo.getFundInfo().getFundCode() + "]");
        DayCostMoneyInfo moneyInfo = costInfo.getCurrentMoneyInfo();

        mPriceItemViewCount.setInfo("持有份额", NumberUtil.floatToString(moneyInfo.getTotalCount()));
        mPriceItemViewMarketPrice.setInfo("市值", NumberUtil.floatToString(moneyInfo.getTotalMarketMoney()));

        float profitMoney = moneyInfo.getTotalMarketMoney() - moneyInfo.getTotalMoney();
        float profitRatio = profitMoney / moneyInfo.getTotalMoney();
        mPriceItemViewLibProfit.setInfo("持仓盈亏", NumberUtil.floatToString(profitMoney), NumberUtil.floatToString(profitRatio * 100) + "%", profitRatio >= 0);

        mPriceItemViewTotalMoney.setInfo("总本金", NumberUtil.floatToString(moneyInfo.getTotalMoney()));
        mPriceItemViewUnitCost.setInfo("单位成本", NumberUtil.floatToString(moneyInfo.getTotalMoney() / moneyInfo.getTotalCount(), 4));

        mPriceItemViewCurrentPrice.setInfo(moneyInfo.getDate() + "净值", NumberUtil.floatToString(moneyInfo.getPrice(), 4));
    }
}
