package com.example.daisongsong.chartapp.dataprocess;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daisongsong on 2016/11/28.
 */

public class DataProcessAdapter extends BaseAdapter {
    private FundPresenter mPresenter;
    private List<DayData> mList;

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FundViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            holder = new FundViewHolder(parent);
            convertView = holder.getView();
            convertView.setTag(holder);
        } else {
            holder = (FundViewHolder) convertView.getTag();
        }

        holder.bindData(position, mList.get(position));

        return convertView;
    }

    public void setData(ArrayList<DayData> data) {
        mList = data;
    }

    public void setPresenter(FundPresenter presenter) {
        mPresenter = presenter;
    }

    private final class FundViewHolder {
        private View mView;
        private TextView mTextViewDate;
        private TextView mTextViewPrice;
        private TextView mTextViewMoney;
        private TextView mTextViewCount;
        private TextView mTextViewTotalCount;
        private TextView mTextViewTotalMoney;
        private TextView mTextViewMarketMoney;
        private TextView mTextViewRatio;
        private TextView mTextViewUnitCost;

        private int mPosition;

        public FundViewHolder(ViewGroup parent) {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_fund_item, parent, false);
            mTextViewDate = (TextView) mView.findViewById(R.id.mTextViewDate);
            mTextViewPrice = (TextView) mView.findViewById(R.id.mTextViewPrice);
            mTextViewMoney = (TextView) mView.findViewById(R.id.mTextViewMoney);
            mTextViewCount = (TextView) mView.findViewById(R.id.mTextViewCount);
            mTextViewTotalCount = (TextView) mView.findViewById(R.id.mTextViewTotalCount);
            mTextViewTotalMoney = (TextView) mView.findViewById(R.id.mTextViewTotalMoney);
            mTextViewMarketMoney = (TextView) mView.findViewById(R.id.mTextViewMarketMoney);
            mTextViewRatio = (TextView) mView.findViewById(R.id.mTextViewRatio);
            mTextViewUnitCost = (TextView) mView.findViewById(R.id.mTextViewUnitCost);

            mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder b = new AlertDialog.Builder(v.getContext());
                    b.setTitle("定投");
                    b.setNegativeButton("设为开始", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.setStart(mPosition);
                        }
                    }).setPositiveButton("设为结束", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.setEnd(mPosition);
                        }
                    });
                    b.show();

                    return true;
                }
            });
        }

        public View getView() {
            return mView;
        }

        public void bindData(int position, DayData data) {
            this.mPosition = position;
            mTextViewDate.setText(data.getDate());
            mTextViewPrice.setText(String.valueOf(data.getPrice()));

            mTextViewMoney.setText(makeString(data.getMoney(), 2));
            mTextViewCount.setText(makeString(data.getCount()));
            mTextViewTotalCount.setText(makeString(data.getTotalCount()));
            mTextViewTotalMoney.setText(makeString(data.getTotalMoney(), 2));
            mTextViewMarketMoney.setText(makeString(data.getMarketMoney(), 2));
            mTextViewRatio.setText(makeString(data.getRatio()));
            mTextViewUnitCost.setText(makeString(data.getUnitCost()));

            if (data.isInProcess()) {
                mView.setBackgroundColor(Color.WHITE);
            } else {
                mView.setBackgroundColor(Color.GRAY);
            }
        }

        private String makeString(float f) {
            return makeString(f, 3);
        }

        private String makeString(float f, int count) {
            String s = "%." + count + "f";
            return String.format(s, f);
        }
    }
}
