package com.example.daisongsong.chartapp.book.fundbuydetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.model.CostInfo;
import com.example.daisongsong.chartapp.book.widget.FundCostView;

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
        mListView.addHeaderView(mFundCostView);
        mAdapter = new FundBuyHistoryAdapter();
        mListView.setAdapter(mAdapter);

        mAdapter.setBuyInfos(mCostInfo.getBuyInfos());
        mAdapter.notifyDataSetChanged();
    }
}
