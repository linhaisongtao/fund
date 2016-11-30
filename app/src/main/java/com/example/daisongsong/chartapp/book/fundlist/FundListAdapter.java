package com.example.daisongsong.chartapp.book.fundlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.model.FundInfo;
import com.example.daisongsong.chartapp.book.price.PriceListActivity;

import java.util.List;

/**
 * Created by daisongsong on 2016/11/30.
 */

public class FundListAdapter extends BaseAdapter {
    private List<FundInfo> mFunds;

    public void setFunds(List<FundInfo> funds) {
        mFunds = funds;
    }

    @Override
    public int getCount() {
        return mFunds == null ? 0 : mFunds.size();
    }

    @Override
    public Object getItem(int position) {
        return mFunds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FundItemViewHolder holder;
        if (convertView == null || convertView.getTag() == null || !(convertView.getTag() instanceof FundItemViewHolder)) {
            holder = new FundItemViewHolder(parent);
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            holder = (FundItemViewHolder) convertView.getTag();
        }
        holder.bind(mFunds.get(position));
        return convertView;
    }

    private static class FundItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewName;
        private FundInfo mFundInfo;

        public FundItemViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fund_list, parent, false));
            mTextViewName = (TextView) itemView.findViewById(R.id.mTextViewName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PriceListActivity.start(v.getContext(), mFundInfo.getFundCode());
                }
            });
        }

        public void bind(FundInfo fundInfo) {
            mFundInfo = fundInfo;
            mTextViewName.setText(fundInfo.getName() + "(" + fundInfo.getFundCode() + ")");
        }
    }
}
