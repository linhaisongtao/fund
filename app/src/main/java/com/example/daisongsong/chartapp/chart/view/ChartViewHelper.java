package com.example.daisongsong.chartapp.chart.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import com.example.daisongsong.chartapp.App;
import com.example.daisongsong.chartapp.R;

/**
 * Created by daisongsong on 2016/11/28.
 */

public class ChartViewHelper {
    public static final int[] COLORS = new int[]{Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.GRAY, Color.CYAN};
    private static final int PADDING = 50;
    private static final int POINT_WIDTH = 3;
    private SurfaceHolder mSurfaceHolder;
    private int mWidth;
    private int mHeight;

    private Rect mRectContent;

    public ChartViewHelper(SurfaceHolder surfaceHolder, int width, int height) {
        mSurfaceHolder = surfaceHolder;
        mWidth = width;
        mHeight = height;

        mRectContent = new Rect(PADDING, PADDING, mWidth - PADDING, mHeight - PADDING);
    }

    public void drawChart(final ChartInfo chartInfo) {
        if (chartInfo == null) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                drawChartBackground(chartInfo);
            }
        }).start();
    }

    private void drawChartBackground(ChartInfo chartInfo) {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        drawAxis(canvas);

        for (int i = 0; i < chartInfo.dSize(); i++) {
            drawD(canvas, chartInfo, i);
        }


        Paint paint = new Paint();
        paint.setColor(Color.RED);
        int fontSize = App.getApp().getResources().getDimensionPixelSize(R.dimen.info_14_dp);
        paint.setTextSize(fontSize);
        float maxY = chartInfo.getMax();
        float minY = chartInfo.getMin();
        canvas.drawText(String.format("%.3f", maxY), 0, fontSize, paint);
        canvas.drawText(String.format("%.3f", minY), 0, mRectContent.height(), paint);

        mSurfaceHolder.unlockCanvasAndPost(canvas);
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
        p.setStrokeWidth(5);
        Rect rect = new Rect(mRectContent.left, mRectContent.top - 40, mRectContent.right, mRectContent.bottom + 40);
        canvas.drawRect(rect, p);
    }

}
