package com.example.daisongsong.chartapp.book;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

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

    public static void start(Context context) {
        context.startActivity(new Intent(context, BookActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

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
    }

    @Override
    public void showList(List<CostInfo> infos) {
        mAdapter.setCostInfos(infos);
        mAdapter.notifyDataSetChanged();
    }
}
