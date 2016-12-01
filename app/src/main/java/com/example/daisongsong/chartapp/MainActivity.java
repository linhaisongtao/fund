package com.example.daisongsong.chartapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.daisongsong.chartapp.book.BookActivity;
import com.example.daisongsong.chartapp.book.BookAdapter;
import com.example.daisongsong.chartapp.book.fundlist.FundListActivity;

import java.io.IOException;

public class MainActivity extends Activity {
    private ListView mListView;
    private FundFileAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.mListView);
        mAdapter = new FundFileAdapter();
        mListView.setAdapter(mAdapter);

        try {
            String[] list = getAssets().list("");
            mAdapter.setPaths(list);
            mAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }

        findViewById(R.id.mTextViewBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookActivity.start(v.getContext());
            }
        });
    }
}
