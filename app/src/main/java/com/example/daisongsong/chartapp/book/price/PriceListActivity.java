package com.example.daisongsong.chartapp.book.price;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.data.FundManager;
import com.example.daisongsong.chartapp.book.model.FundInfo;
import com.example.daisongsong.chartapp.book.price.add.PriceAddActivity;

import java.util.List;

/**
 * Created by daisongsong on 2016/11/30.
 */

public class PriceListActivity extends Activity {
    private TextView mTextViewTitle;
    private ListView mListView;
    private PriceListAdapter mAdapter;

    private FundInfo mFundInfo;

    public static void start(Context context, String code) {
        Intent i = new Intent(context, PriceListActivity.class);
        i.putExtra("code", code);
        context.startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list);

        mTextViewTitle = (TextView) findViewById(R.id.mTextViewTitle);

        findViewById(R.id.mTextViewAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PriceAddActivity.start(PriceListActivity.this, 1, mFundInfo);
            }
        });


        String code = getIntent().getStringExtra("code");
        List<FundInfo> allFunds = FundManager.getAllFunds();
        for (FundInfo allFund : allFunds) {
            if (allFund.getFundCode().equalsIgnoreCase(code)) {
                mFundInfo = allFund;
                break;
            }
        }

        mTextViewTitle.setText(mFundInfo.getName());

        mListView = (ListView) findViewById(R.id.mListView);
        mAdapter = new PriceListAdapter();
        mListView.setAdapter(mAdapter);
        notifyDataChange();
    }

    private void notifyDataChange() {
        List<FundInfo.FundPrice> allFundPrice = FundManager.getAllFundPrice(mFundInfo.getFundCode());
        mAdapter.setPrices(allFundPrice);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            notifyDataChange();
        }
    }
}
