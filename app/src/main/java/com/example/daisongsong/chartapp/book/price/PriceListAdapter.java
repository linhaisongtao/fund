package com.example.daisongsong.chartapp.book.price;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.model.FundInfo;

import java.util.List;

/**
 * Created by daisongsong on 2016/12/1.
 */

public class PriceListAdapter extends BaseAdapter{
    private List<FundInfo.FundPrice> mPrices;

    @Override
    public int getCount() {
        return mPrices == null ? 0 : mPrices.size();
    }

    @Override
    public Object getItem(int position) {
        return mPrices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PriceItemViewHolder holder;
        if (convertView == null || convertView.getTag() == null || !(convertView.getTag() instanceof PriceItemViewHolder)) {
            holder = new PriceItemViewHolder(parent);
            convertView = holder.getView();
            convertView.setTag(holder);
        } else {
            holder = (PriceItemViewHolder) convertView.getTag();
        }

        holder.bind(mPrices.get(position));

        return convertView;
    }

    public void setPrices(List<FundInfo.FundPrice> prices) {
        mPrices = prices;
    }

    private static final class PriceItemViewHolder {
        private View mView;
        private TextView mTextViewPrice;
        private TextView mTextViewDate;

        public PriceItemViewHolder(ViewGroup parent) {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fund_price_list, parent, false);
            mTextViewDate = (TextView) mView.findViewById(R.id.mTextViewDate);
            mTextViewPrice = (TextView) mView.findViewById(R.id.mTextViewPrice);
        }

        public View getView() {
            return mView;
        }

        public void bind(FundInfo.FundPrice fundPrice) {
            mTextViewDate.setText(fundPrice.getDate());
            mTextViewPrice.setText("净值:" + fundPrice.getPrice());
        }
    }
}
