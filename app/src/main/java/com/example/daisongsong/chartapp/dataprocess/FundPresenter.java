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
    private float mMaxY = Float.MAX_VALUE;

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

        mMaxY = Float.MIN_VALUE;

        String[] x = new String[mEnd - mStart + 1];
        ArrayList<Float> price = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            if (i >= mStart && i <= mEnd) {
                DayData data = mDatas.get(i);
                x[i - mStart] = data.getDate();

                float p = data.getPrice();
                price.add(p);
                mMaxY = Math.max(mMaxY, p);
            }
        }

        info.setX(x);
        info.addY(price, "净值");
        info.addY(makeY(1), "日定投");
        info.addY(makeY(5), "周定投");
        info.addY(makeY(10), "2周定投");
        info.addY(makeY(22), "月定投");

        info.setMax(mMaxY);
        info.setMin(0f);
        info.setWidth(mMaxY);
        info.setNeedInit(false);

        return info;
    }

    private ArrayList<Float> makeY(int step) {
        ArrayList<Float> y = new ArrayList<>();
        process(mStart, mEnd, step);
        for (int i = 0; i < mDatas.size(); i++) {
            if (i >= mStart && i <= mEnd) {
                float c = mDatas.get(i).getUnitCost();
                y.add(c);
                mMaxY = Math.max(mMaxY, c);
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
        info.addY(makeRatioY(1), "日定投");
        info.addY(makeRatioY(5), "周定投");
        info.addY(makeRatioY(10), "2周定投");
        info.addY(makeRatioY(20), "月定投");
        return info;
    }

    private ArrayList<Float> makeRatioY(int step) {
        ArrayList<Float> y = new ArrayList<>();
        process(mStart, mEnd, step);
        for (int i = 0; i < mDatas.size(); i++) {
            if (i >= mStart && i <= mEnd) {
                float r = mDatas.get(i).getRatio();
                y.add(r);
            }
        }
        return y;
    }

    public interface IView {
        void showList(ArrayList<DayData> list);

        int getStep();
    }
}
