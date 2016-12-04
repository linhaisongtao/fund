package com.example.daisongsong.chartapp.chart.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.daisongsong.chartapp.App;
import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.util.NumberUtil;

import java.util.ArrayList;

/**
 * Created by daisongsong on 2016/11/28.
 */

public class ChartViewHelper {
    public static final int[] COLORS = new int[]{Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.GRAY, Color.CYAN};
    private static final int PADDING = App.getApp().getResources().getDimensionPixelSize(R.dimen.info_20_dp);
    private static final int POINT_WIDTH = 3;
    private SurfaceHolder mSurfaceHolder;
    private SurfaceView mSurfaceView;
    private int mWidth;
    private int mHeight;

    private Rect mRectContent;
    private ChartInfo mChartInfo;
    private OnChartTouchListener mListener;
    private SparseArray<Boolean> mPositionEnableStatus = new SparseArray<>();

    public ChartViewHelper(SurfaceHolder surfaceHolder, int width, int height) {
        mSurfaceHolder = surfaceHolder;
        mWidth = width;
        mHeight = height;

        mRectContent = new Rect(PADDING, PADDING, (int) (mWidth - PADDING * 1.5), mHeight - PADDING);
    }

    public ChartViewHelper(final SurfaceView surfaceView, SurfaceHolder holder, int width, int height) {
        this(holder, width, height);
        mSurfaceView = surfaceView;

        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        drawPointInfo(event.getX(), event.getY());
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        drawPointInfo(event.getX(), event.getY());
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        clearPointInfo();
                        return true;
                }
                return false;
            }
        });
    }

    private void clearPointInfo() {
        if (mListener != null) {
            mListener.cancelMessage();
        }
    }

    private void drawPointInfo(float x, float y) {
        int count = mChartInfo.getX().length;
        float step = (float) (1.0 * mRectContent.width() / (count - 1));
        int position = (int) (((int) x - mRectContent.left) / step);
        if (position >= 0 && position < count && mListener != null) {
            String xMessage = mChartInfo.getX()[position];
            String yMessage = "";
            ArrayList<ArrayList<Float>> mChartInfoY = mChartInfo.getY();
            for (int i = 0; i < mChartInfoY.size(); i++) {
                yMessage += NumberUtil.floatToString(mChartInfoY.get(i).get(position), 3);
                if (i != (mChartInfoY.size() - 1)) {
                    yMessage += ",";
                }
            }
            mListener.onTouched(x, y, xMessage, yMessage);
        }
    }

    public void drawChart(final ChartInfo chartInfo) {
        if (chartInfo == null) {
            return;
        }
        this.mChartInfo = chartInfo;

        new Thread(new Runnable() {
            @Override
            public void run() {
                drawChartBackground(chartInfo);
            }
        }).start();
    }

    private void drawChartBackground(ChartInfo chartInfo) {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        canvas.drawColor(Color.BLACK);
        drawAxis(canvas);

        for (int i = 0; i < chartInfo.dSize(); i++) {
            if(isYEnabled(i)) {
                drawD(canvas, chartInfo, i);
            }
        }

        drawYValues(chartInfo, canvas);

        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    private void drawYValues(ChartInfo chartInfo, Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        int fontSize = App.getApp().getResources().getDimensionPixelSize(R.dimen.info_14_dp);
        paint.setTextSize(fontSize);
        float maxY = chartInfo.getMax();
        float minY = chartInfo.getMin();
        canvas.drawText(String.format("%.3f", maxY), 0, fontSize, paint);
        canvas.drawText(String.format("%.3f", minY), 0, mRectContent.height(), paint);
    }

    private void drawD(Canvas canvas, ChartInfo chartInfo, int d) {
        chartInfo.init();
        Paint p = new Paint();
        p.setColor(COLORS[d]);
        p.setStrokeWidth(POINT_WIDTH);

        int count = chartInfo.getX().length;
        float step = (float) (1.0 * mRectContent.width() / (count - 1));
        float yStep = (float) (1.0 * mRectContent.height() / (chartInfo.width()));

        Integer lastX = null;
        Integer lastY = null;

        for (int i = 0; i < count; ++i) {
            int x = (int) (PADDING + step * i);
            int y = (int) (PADDING + mRectContent.height() - yStep * (chartInfo.getY(d).get(i) - chartInfo.getMin()));
            canvas.drawCircle(x, y, POINT_WIDTH, p);

            if (lastX != null) {
                canvas.drawLine(lastX, lastY, x, y, p);
            }
            lastX = x;
            lastY = y;

            if (i == (count - 1)) {
                int fontSize = App.getApp().getResources().getDimensionPixelSize(R.dimen.info_8_dp);
                p.setTextSize(fontSize);
                canvas.drawText(String.format("%.3f", chartInfo.getY(d).get(i)), x, y + fontSize / 2, p);
            }

        }

    }

    private void drawAxis(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setStrokeWidth(App.getApp().getResources().getDimensionPixelSize(R.dimen.info_1_dp));
        p.setStyle(Paint.Style.STROKE);
        Rect rect = new Rect(mRectContent.left, mRectContent.top - 40, mRectContent.right, mRectContent.bottom + 40);
        canvas.drawRect(rect, p);
    }

    public void setListener(OnChartTouchListener listener) {
        mListener = listener;
    }

    public void setYEnabled(int i, boolean selected) {
        mPositionEnableStatus.put(i, selected);
        new Thread(new Runnable() {
            @Override
            public void run() {
                drawChartBackground(mChartInfo);
            }
        }).start();
    }

    private boolean isYEnabled(int y){
        Boolean aBoolean = mPositionEnableStatus.get(y);
        if(aBoolean != null){
            return aBoolean;
        }else {
            return true;
        }
    }

    public static interface OnChartTouchListener {
        void onTouched(float x, float y, String xMessage, String yMessage);

        void cancelMessage();
    }
}
