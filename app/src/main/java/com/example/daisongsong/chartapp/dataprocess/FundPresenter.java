package com.example.daisongsong.chartapp.dataprocess;

import com.example.daisongsong.chartapp.chart.view.ChartInfo;

import java.util.ArrayList;

/**
 * Created by daisongsong on 2016/11/28.
 */

public class FundPresenter {
    private float mUnitMoney = 100f;
    private IView mView;

    private ArrayList<FundData> mOriginList = new ArrayList<>();
    private ArrayList<DayData> mDatas;
    private int mStart;
    private int mEnd;

    public FundPresenter(IView view, String path) {
        mView = view;
        mOriginList = FileHelper.fromFile(path);
    }

    public int getCount() {
        return mOriginList == null ? 0 : mOriginList.size();
    }

    public void process(int start, int end) {
        this.mStart = start;
        this.mEnd = end;
        int step = mView.getStep();

        mDatas = new ArrayList<>(mOriginList.size());
        for (int i = 0; i < mOriginList.size(); i++) {
            DayData d = new DayData(mOriginList.get(i));
            if (i >= start && i <= end) {
                if ((i - start) % step == 0) {
                    d.setMoney(mUnitMoney);
                }
                d.setInProcess(true);
            } else {
                d.setInProcess(false);
            }
            mDatas.add(d);
        }

        DayData mLastData = null;
        for (int i = 0; i < mDatas.size(); i++) {
            DayData d = mDatas.get(i);
            d.setCount(d.getMoney() / d.getPrice());
            d.setTotalMoney(d.getMoney());
            d.setTotalCount(d.getCount());

            if (mLastData != null) {
                d.setTotalCount(mLastData.getTotalCount() + d.getCount());
                d.setTotalMoney(mLastData.getTotalMoney() + d.getMoney());
            }

            d.setMarketMoney(d.getPrice() * d.getTotalCount());
            d.setRatio((d.getMarketMoney() - d.getTotalMoney()) / d.getTotalMoney());
            d.setUnitCost(d.getTotalMoney() / d.getTotalCount());

            mLastData = d;
        }

        mView.showList(mDatas);
    }

    public ChartInfo makeChartInfo() {
        ChartInfo info = new ChartInfo();
        String[] x = new String[mEnd - mStart + 1];
        ArrayList<Float> unit = new ArrayList<>();
        ArrayList<Float> price = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            if (i >= mStart && i <= mEnd) {
                DayData data = mDatas.get(i);
                x[i - mStart] = data.getDate();
                unit.add(data.getUnitCost());
                price.add(data.getPrice());
            }
        }

        info.setX(x);
        ArrayList<ArrayList<Float>> y = new ArrayList<>();
        y.add(price);
        y.add(unit);
        info.setY(y);
        return info;
    }

    public void process() {
        process(mStart, mEnd);
    }

    public void setStart(int start) {
        mStart = start;
        process();
    }

    public void setEnd(int end) {
        mEnd = end;
        process();
    }

    public interface IView {
        void showList(ArrayList<DayData> list);

        int getStep();
    }
}
