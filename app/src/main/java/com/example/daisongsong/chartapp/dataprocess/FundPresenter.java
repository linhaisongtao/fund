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

    public void process(int start, int end, int step) {
        this.mStart = start;
        this.mEnd = end;

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
        ArrayList<Float> price = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            if (i >= mStart && i <= mEnd) {
                DayData data = mDatas.get(i);
                x[i - mStart] = data.getDate();
                price.add(data.getPrice());
            }
        }

        info.setX(x);
        ArrayList<ArrayList<Float>> y = new ArrayList<>();
        y.add(price);

        y.add(makeY(1));
        y.add(makeY(5));
        y.add(makeY(10));
        y.add(makeY(20));

        info.setY(y);
        return info;
    }

    private ArrayList<Float> makeY(int step) {
        ArrayList<Float> y = new ArrayList<>();
        process(mStart, mEnd, step);
        for (int i = 0; i < mDatas.size(); i++) {
            if (i >= mStart && i <= mEnd) {
                y.add(mDatas.get(i).getUnitCost());
            }
        }
        return y;
    }

    public void process() {
        process(mStart, mEnd, mView.getStep());
    }

    public void setStart(int start) {
        mStart = start;
        process();
    }

    public void setEnd(int end) {
        mEnd = end;
        process();
    }

    public ChartInfo makeRatioChartInfo() {
        ChartInfo info = new ChartInfo();
        String[] x = new String[mEnd - mStart + 1];
        ArrayList<Float> priceRatio = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            if (i >= mStart && i <= mEnd) {
                DayData data = mDatas.get(i);
                x[i - mStart] = data.getDate();
                priceRatio.add(data.getPrice());
            }
        }

        info.setX(x);
        ArrayList<ArrayList<Float>> y = new ArrayList<>();
//        y.add(priceRatio);

        y.add(makeRatioY(1));
        y.add(makeRatioY(5));
        y.add(makeRatioY(10));
        y.add(makeRatioY(20));

        info.setY(y);
        return info;
    }

    private ArrayList<Float> makeRatioY(int step) {
        ArrayList<Float> y = new ArrayList<>();
        process(mStart, mEnd, step);
        for (int i = 0; i < mDatas.size(); i++) {
            if (i >= mStart && i <= mEnd) {
                y.add(mDatas.get(i).getRatio());
            }
        }
        return y;
    }

    public interface IView {
        void showList(ArrayList<DayData> list);

        int getStep();
    }
}
