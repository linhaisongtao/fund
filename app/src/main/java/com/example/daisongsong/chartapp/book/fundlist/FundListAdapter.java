package com.example.daisongsong.chartapp.book.fundlist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.data.FundManager;
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
            convertView = holder.getView();
            convertView.setTag(holder);
        } else {
            holder = (FundItemViewHolder) convertView.getTag();
        }
        holder.bind(mFunds.get(position));
        return convertView;
    }

    private class FundItemViewHolder {
        private View mView;
        private TextView mTextViewName;
        private FundInfo mFundInfo;

        public FundItemViewHolder(ViewGroup parent) {
            mView = (LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fund_list, parent, false));
            mTextViewName = (TextView) mView.findViewById(R.id.mTextViewName);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PriceListActivity.start(v.getContext(), mFundInfo.getFundCode());
                }
            });

            mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("删除")
                            .setMessage("确定删除【" + mFundInfo.getName() + "】")
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mFunds.remove(mFundInfo);
                                    FundManager.writeAllFunds(mFunds);
                                    notifyDataSetChanged();
                                }
                            })
                            .setCancelable(true)
                            .show();
                    return true;
                }
            });
        }

        public void bind(FundInfo fundInfo) {
            mFundInfo = fundInfo;
            mTextViewName.setText(fundInfo.getName() + "(" + fundInfo.getFundCode() + ")");
        }

        public View getView() {
            return mView;
        }
    }
}
