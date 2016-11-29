package com.example.daisongsong.chartapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.daisongsong.chartapp.dataprocess.DataProcessActivity;

/**
 * Created by daisongsong on 2016/11/29.
 */

public class FundFileAdapter extends BaseAdapter {
    private String[] mPaths;

    public void setPaths(String[] paths) {
        mPaths = paths;
    }

    @Override
    public int getCount() {
        return mPaths == null ? 0 : mPaths.length;
    }

    @Override
    public Object getItem(int position) {
        return mPaths[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fund_file, parent, false);
        }
        TextView mTextViewFile = (TextView) convertView.findViewById(R.id.mTextViewFile);
        mTextViewFile.setText(mPaths[position]);

        final Context context = convertView.getContext();
        mTextViewFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataProcessActivity.start(context, mPaths[position]);
            }
        });
        return convertView;
    }
}
