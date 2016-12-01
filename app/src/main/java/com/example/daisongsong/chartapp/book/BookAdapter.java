package com.example.daisongsong.chartapp.book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.model.CostInfo;
import com.example.daisongsong.chartapp.book.model.DayCostMoneyInfo;

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
        private TextView mTextViewName;
        private TextView mTextViewTotalMoney;
        private TextView mTextViewMarketMoney;
        private TextView mTextViewTotalCount;

        public CostItemViewHolder(ViewGroup parent) {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cost, parent, false);
            mTextViewName = (TextView) mView.findViewById(R.id.mTextViewName);
            mTextViewTotalMoney = (TextView) mView.findViewById(R.id.mTextViewTotalMoney);
            mTextViewMarketMoney = (TextView) mView.findViewById(R.id.mTextViewMarketMoney);
            mTextViewTotalCount = (TextView) mView.findViewById(R.id.mTextViewTotalCount);
        }

        public View getView() {
            return mView;
        }

        public void bind(CostInfo costInfo) {
            mTextViewName.setText(costInfo.getFundInfo().getName() + "[" + costInfo.getFundInfo().getFundCode() + "]");
            DayCostMoneyInfo moneyInfo = costInfo.getCurrentMoneyInfo();
            mTextViewTotalMoney.setText("本金:" + moneyInfo.getTotalMoney());
            mTextViewMarketMoney.setText("市值:" + moneyInfo.getTotalMarketMoney());
            mTextViewTotalCount.setText("份额:" + moneyInfo.getTotalCount());
        }
    }
}
