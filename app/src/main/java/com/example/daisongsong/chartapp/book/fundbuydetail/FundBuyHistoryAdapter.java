package com.example.daisongsong.chartapp.book.fundbuydetail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.model.BuyInfo;

import java.util.ArrayList;

/**
 * Created by daisongsong on 2016/12/1.
 */

public class FundBuyHistoryAdapter extends BaseAdapter {
    private ArrayList<BuyInfo> mBuyInfos;

    @Override
    public int getCount() {
        return mBuyInfos == null ? 0 : mBuyInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mBuyInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FundBuyItemViewHolder holder;
        if (convertView == null || convertView.getTag() == null || !(convertView.getTag() instanceof FundBuyItemViewHolder)) {
            holder = new FundBuyItemViewHolder(parent);
            convertView = holder.getView();
            convertView.setTag(holder);
        } else {
            holder = (FundBuyItemViewHolder) convertView.getTag();
        }
        holder.bind(mBuyInfos.get(position), position);
        return convertView;
    }

    public void setBuyInfos(ArrayList<BuyInfo> buyInfos) {
        mBuyInfos = buyInfos;
    }

    private class FundBuyItemViewHolder {
        private View mView;
        private TextView mTextViewMoney;
        private TextView mTextViewPrice;
        private TextView mTextViewCount;
        private TextView mTextViewDate;
        private View mViewFirstDivider;
        private TextView mTextViewHistory;


        public FundBuyItemViewHolder(ViewGroup parent) {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fund_buy_view, parent, false);
            mTextViewMoney = (TextView) mView.findViewById(R.id.mTextViewMoney);
            mTextViewPrice = (TextView) mView.findViewById(R.id.mTextViewPrice);
            mTextViewCount = (TextView) mView.findViewById(R.id.mTextViewCount);
            mTextViewDate = (TextView) mView.findViewById(R.id.mTextViewDate);
            mTextViewHistory = (TextView) mView.findViewById(R.id.mTextViewHistory);
            mViewFirstDivider = mView.findViewById(R.id.mViewFirstDivider);
        }

        public View getView() {
            return mView;
        }

        public void bind(BuyInfo buyInfo, int position) {
            mTextViewMoney.setText("金额:" + buyInfo.getMoney());
            mTextViewPrice.setText("单价:" + buyInfo.getFundPrice().getPrice());
            mTextViewCount.setText("份额:" + buyInfo.getCount());
            mTextViewDate.setText(buyInfo.getFundPrice().getDate());

            if(position == 0){
                mViewFirstDivider.setVisibility(View.VISIBLE);
                mTextViewHistory.setVisibility(View.VISIBLE);
            }else {
                mViewFirstDivider.setVisibility(View.GONE);
                mTextViewHistory.setVisibility(View.GONE);
            }
        }
    }
}
