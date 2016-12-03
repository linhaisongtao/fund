package com.example.daisongsong.chartapp.book.price;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.buy.BuyFundActivity;
import com.example.daisongsong.chartapp.book.data.FundManager;
import com.example.daisongsong.chartapp.book.model.FundPrice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by daisongsong on 2016/12/1.
 */

public class PriceListAdapter extends BaseAdapter {
    private List<FundPrice> mPrices;

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

    public void setPrices(List<FundPrice> prices) {
        mPrices = new ArrayList<>();
        if(prices != null){
            mPrices.addAll(prices);
            Collections.reverse(mPrices);
        }
    }

    private final class PriceItemViewHolder {
        private View mView;
        private TextView mTextViewPrice;
        private TextView mTextViewDate;

        private FundPrice mFundPrice;

        public PriceItemViewHolder(ViewGroup parent) {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fund_price_list, parent, false);
            mTextViewDate = (TextView) mView.findViewById(R.id.mTextViewDate);
            mTextViewPrice = (TextView) mView.findViewById(R.id.mTextViewPrice);

            mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("添加申购")
                            .setPositiveButton("申购", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    BuyFundActivity.start(mView.getContext(), mFundPrice);
                                }
                            })
                            .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mPrices.remove(mFundPrice);

                                    List<FundPrice> prices = new ArrayList<FundPrice>();
                                    prices.addAll(mPrices);
                                    Collections.reverse(prices);
                                    FundManager.writeAllFundPrice(mFundPrice.getFundInfo().getFundCode(), prices);

                                    notifyDataSetChanged();
                                }
                            })
                            .setCancelable(true)
                            .show();
                    return true;
                }
            });
        }

        public View getView() {
            return mView;
        }

        public void bind(FundPrice fundPrice) {
            mFundPrice = fundPrice;
            mTextViewDate.setText(fundPrice.getDate());
            mTextViewPrice.setText("净值:" + fundPrice.getPrice());
        }
    }
}
