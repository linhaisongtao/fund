package com.example.daisongsong.chartapp.dataprocess;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.chart.ChartActivity;

import java.util.ArrayList;

/**
 * Created by daisongsong on 2016/11/28.
 */

public class DataProcessActivity extends Activity implements FundPresenter.IView {
    private ListView mListView;
    private EditText mEditTextStep;
    private DataProcessAdapter mAdapter;

    private FundPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_process);

        mListView = (ListView) findViewById(R.id.mListView);
        mEditTextStep = (EditText) findViewById(R.id.mEditTextStep);

        mListView.addHeaderView(makeHeaderView());
        mAdapter = new DataProcessAdapter();
        mListView.setAdapter(mAdapter);

        mPresenter = new FundPresenter(this);
        mAdapter.setPresenter(mPresenter);

        mPresenter.process(0, mPresenter.getCount() - 1, 1);

        findViewById(R.id.mButtonChart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartActivity.start(DataProcessActivity.this, mPresenter.makeChartInfo());
            }
        });

        findViewById(R.id.mButtonRatio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartActivity.start(DataProcessActivity.this, mPresenter.makeRatioChartInfo());
          }
        });

    }

    private View makeHeaderView() {
        View view = LayoutInflater.from(this).inflate(R.layout.view_fund_item, null);
        return view;
    }

    @Override
    public void showList(ArrayList<DayData> list) {
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public int getStep() {
        return Integer.parseInt(mEditTextStep.getText().toString());
    }
}
