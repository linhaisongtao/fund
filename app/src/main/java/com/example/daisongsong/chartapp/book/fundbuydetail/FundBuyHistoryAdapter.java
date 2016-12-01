package com.example.daisongsong.chartapp.book.fundbuydetail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.data.FundManager;
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

        private BuyInfo mBuyInfo;

        public FundBuyItemViewHolder(ViewGroup parent) {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fund_buy_view, parent, false);
            mTextViewMoney = (TextView) mView.findViewById(R.id.mTextViewMoney);
            mTextViewPrice = (TextView) mView.findViewById(R.id.mTextViewPrice);
            mTextViewCount = (TextView) mView.findViewById(R.id.mTextViewCount);
            mTextViewDate = (TextView) mView.findViewById(R.id.mTextViewDate);
            mTextViewHistory = (TextView) mView.findViewById(R.id.mTextViewHistory);
            mViewFirstDivider = mView.findViewById(R.id.mViewFirstDivider);

            mView.findViewById(R.id.mViewGroupContent).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("删除申购记录")
                            .setMessage("确定要删除[" + mBuyInfo.getFundPrice().getDate() + "]的记录")
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mBuyInfos.remove(mBuyInfo);
                                    FundManager.saveBuyInfos(mBuyInfo.getFundPrice().getFundInfo().getFundCode(), mBuyInfos);
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

        public void bind(BuyInfo buyInfo, int position) {
            this.mBuyInfo = buyInfo;

            mTextViewMoney.setText("金额:" + buyInfo.getMoney());
            mTextViewPrice.setText("单价:" + buyInfo.getFundPrice().getPrice());
            mTextViewCount.setText("份额:" + buyInfo.getCount());
            mTextViewDate.setText(buyInfo.getFundPrice().getDate());

            if (position == 0) {
                mViewFirstDivider.setVisibility(View.VISIBLE);
                mTextViewHistory.setVisibility(View.VISIBLE);
            } else {
                mViewFirstDivider.setVisibility(View.GONE);
                mTextViewHistory.setVisibility(View.GONE);
            }
        }
    }
}
