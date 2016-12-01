package com.example.daisongsong.chartapp.book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.fundbuydetail.FundBuyDetailActivity;
import com.example.daisongsong.chartapp.book.model.CostInfo;
import com.example.daisongsong.chartapp.book.widget.FundCostView;

import java.util.List;

/**
 * Created by daisongsong on 2016/12/1.
 */

public class BookAdapter extends BaseAdapter {
    private List<CostInfo> mCostInfos;

    public void setCostInfos(List<CostInfo> costInfos) {
        mCostInfos = costInfos;
    }

    @Override
    public int getCount() {
        return mCostInfos == null ? 0 : mCostInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mCostInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CostItemViewHolder holder;
        if (convertView == null || convertView.getTag() == null || !(convertView.getTag() instanceof CostItemViewHolder)) {
            holder = new CostItemViewHolder(parent);
            convertView = holder.getView();
            convertView.setTag(holder);
        } else {
            holder = (CostItemViewHolder) convertView.getTag();
        }
        holder.bind(mCostInfos.get(position));
        return convertView;
    }

    private class CostItemViewHolder {
        private View mView;
        private FundCostView mFundCostView;
        private CostInfo mCostInfo;

        public CostItemViewHolder(ViewGroup parent) {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_list, parent, false);
            mFundCostView = (FundCostView) mView.findViewById(R.id.mFundCostView);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FundBuyDetailActivity.start(v.getContext(), mCostInfo);
                }
            });
        }

        public View getView() {
            return mView;
        }

        public void bind(CostInfo costInfo) {
            this.mCostInfo = costInfo;
            mFundCostView.refreshView(costInfo);
        }
    }
}
