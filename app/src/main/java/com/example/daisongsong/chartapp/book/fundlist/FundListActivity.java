package com.example.daisongsong.chartapp.book.fundlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.addfund.AddFundActivity;
import com.example.daisongsong.chartapp.book.data.FundManager;

/**
 * Created by daisongsong on 2016/11/30.
 */

public class FundListActivity extends Activity {
    private ListView mListView;
    private FundListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_list);

        mListView = (ListView) findViewById(R.id.mListView);
        mAdapter = new FundListAdapter();
        mListView.setAdapter(mAdapter);
        mAdapter.setFunds(FundManager.getAllFunds());

        findViewById(R.id.mTextViewAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFundActivity.startForResult(FundListActivity.this, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            mAdapter.setFunds(FundManager.getAllFunds());
            mAdapter.notifyDataSetChanged();
        }
    }
}
