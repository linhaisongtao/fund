package com.example.daisongsong.chartapp.chart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.chart.view.ChartInfo;
import com.example.daisongsong.chartapp.chart.view.ChartViewHelper;

/**
 * Created by daisongsong on 2016/11/28.
 */

public class ChartActivity extends Activity implements ChartViewHelper.OnChartTouchListener {
    private SurfaceView mSurfaceView;
    private LinearLayout mLinearLayoutTip;
    private FrameLayout mFrameLayout;
    private View mViewYLine;
    private TextView mTextViewX;
    private ChartInfo mChartInfo;
    private ChartViewHelper mHelper;

    public static void start(Context context, ChartInfo datas) {
        Intent i = new Intent(context, ChartActivity.class);
        i.putExtra("data", datas);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        mSurfaceView = (SurfaceView) findViewById(R.id.mSurfaceView);
        mFrameLayout = (FrameLayout) findViewById(R.id.mFrameLayout);
        mTextViewX = (TextView) findViewById(R.id.mTextViewX);
        mViewYLine = findViewById(R.id.mViewYLine);
        mChartInfo = (ChartInfo) getIntent().getSerializableExtra("data");
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                mHelper = new ChartViewHelper(mSurfaceView, surfaceHolder, mSurfaceView.getMeasuredWidth(), mSurfaceView.getMeasuredHeight());
                mHelper.drawChart(mChartInfo);
                mHelper.setListener(ChartActivity.this);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });

        mLinearLayoutTip = (LinearLayout) findViewById(R.id.mLinearLayoutTip);
        int count = Math.min(ChartViewHelper.COLORS.length, mChartInfo.getYNames().size());
        for (int i = 0; i < count; i++) {
            final TextView v = new TextView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            v.setLayoutParams(lp);
            v.setTextSize(14);
            v.setGravity(Gravity.CENTER);
            v.setText(mChartInfo.getYNames().get(i));
            v.setTextColor(getResources().getColorStateList(R.color.selector_a));
            v.setBackgroundColor(ChartViewHelper.COLORS[i]);
            v.setSelected(true);
            final int finalI = i;
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v1) {
                    v.setSelected(!v.isSelected());
                    mHelper.setYEnabled(finalI, v.isSelected());
                }
            });
            mLinearLayoutTip.addView(v);
        }
    }

    @Override
    public void onTouched(float x, float y, String xMessage, String yMessage) {
        mTextViewX.setText(xMessage + " " + yMessage);
        mTextViewX.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mViewYLine.getLayoutParams();
        lp.leftMargin = (int) x;
        mViewYLine.setLayoutParams(lp);
        mViewYLine.setVisibility(View.VISIBLE);
    }

    @Override
    public void cancelMessage() {
        mTextViewX.setVisibility(View.INVISIBLE);
        mViewYLine.setVisibility(View.INVISIBLE);
    }

}
