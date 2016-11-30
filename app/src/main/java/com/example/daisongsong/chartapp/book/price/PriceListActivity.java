package com.example.daisongsong.chartapp.book.price;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.data.FundManager;
import com.example.daisongsong.chartapp.book.model.FundInfo;

import java.util.List;

/**
 * Created by daisongsong on 2016/11/30.
 */

public class PriceListActivity extends Activity {
    private TextView mTextViewTitle;
    private ListView mListView;

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
        mListView = (ListView) findViewById(R.id.mListView);


        String code = getIntent().getStringExtra("code");
        List<FundInfo> allFunds = FundManager.getAllFunds();
        for (FundInfo allFund : allFunds) {
            if (allFund.getFundCode().equalsIgnoreCase(code)) {
                mFundInfo = allFund;
                break;
            }
        }

        mTextViewTitle.setText(mFundInfo.getName());
    }
}
