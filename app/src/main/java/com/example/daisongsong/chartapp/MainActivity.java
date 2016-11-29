package com.example.daisongsong.chartapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

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
    }
}
