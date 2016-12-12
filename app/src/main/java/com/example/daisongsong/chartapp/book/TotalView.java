package com.example.daisongsong.chartapp.book;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.widget.PriceItemView;
import com.example.daisongsong.chartapp.util.NumberUtil;

/**
 * Created by daisongsong on 2016/12/12.
 */

public class TotalView extends FrameLayout {
    private PriceItemView mPriceItemViewTotalMoney;
    private PriceItemView mPriceItemViewTotalMarketPrice;
    private PriceItemView mPriceItemViewTotalRatio;

    private View mViewRoot;

    public TotalView(Context context) {
        super(context);
        init();
    }

    public TotalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TotalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_total, this);
        mPriceItemViewTotalMoney = (PriceItemView) findViewById(R.id.mPriceItemViewTotalMoney);
        mPriceItemViewTotalMarketPrice = (PriceItemView) findViewById(R.id.mPriceItemViewTotalMarketPrice);
        mPriceItemViewTotalRatio = (PriceItemView) findViewById(R.id.mPriceItemViewTotalRatio);
        mViewRoot = findViewById(R.id.mViewRoot);
    }


    public void refreshTotalInfo(float totalMoney, float totalMarketMoney) {
        float profit = totalMarketMoney - totalMoney;
        float ratio = profit / totalMoney;
        mPriceItemViewTotalMoney.setInfo("总本金", NumberUtil.floatToString(totalMoney));
        mPriceItemViewTotalMarketPrice.setInfo("总市值", NumberUtil.floatToString(totalMarketMoney));
        mPriceItemViewTotalRatio.setInfo("盈亏率", NumberUtil.floatToString(profit), NumberUtil.floatToString(ratio * 100) + "%", ratio >= 0f);
    }
}
