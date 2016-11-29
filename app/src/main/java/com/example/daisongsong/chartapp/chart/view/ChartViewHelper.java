package com.example.daisongsong.chartapp.chart.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

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
                p.setTextSize(12);
                canvas.drawText(String.valueOf(chartInfo.getY(d).get(i)), x + 14, y + 14, p);
            }

        }

    }

    private void drawAxis(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setStrokeWidth(5);
        Rect rect = new Rect(mRectContent.left, mRectContent.top - 20, mRectContent.right, mRectContent.bottom + 20);
        canvas.drawRect(rect, p);
    }

}
