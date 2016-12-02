package com.example.daisongsong.chartapp.book;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.example.daisongsong.chartapp.MainActivity;
import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.fundlist.FundListActivity;
import com.example.daisongsong.chartapp.book.model.CostInfo;

import java.util.List;

/**
 * Created by daisongsong on 2016/11/30.
 */

public class BookActivity extends Activity implements BookPresenter.View {
    private BookPresenter mPresenter;
    private ListView mListView;
    private BookAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static void start(Context context) {
        context.startActivity(new Intent(context, BookActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout);
        mListView = (ListView) findViewById(R.id.mListView);
        mAdapter = new BookAdapter();
        mListView.setAdapter(mAdapter);

        mPresenter = new BookPresenter(this);
        mPresenter.computeBooks();

        findViewById(R.id.mTextViewAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FundListActivity.start(v.getContext());
            }
        });

        findViewById(R.id.mTextViewInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.start(v.getContext());
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.computeBooks();
            }
        });
    }

    @Override
    public void showList(List<CostInfo> infos) {
        mAdapter.setCostInfos(infos);
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
