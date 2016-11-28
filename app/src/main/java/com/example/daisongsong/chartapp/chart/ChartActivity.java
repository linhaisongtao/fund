package com.example.daisongsong.chartapp.chart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.chart.view.ChartInfo;
import com.example.daisongsong.chartapp.chart.view.ChartViewHelper;

/**
 * Created by daisongsong on 2016/11/28.
 */

public class ChartActivity extends Activity {
    private SurfaceView mSurfaceView;

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

        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                ChartViewHelper helper = new ChartViewHelper(surfaceHolder, mSurfaceView.getMeasuredWidth(), mSurfaceView.getMeasuredHeight());
                helper.drawChart((ChartInfo) getIntent().getSerializableExtra("data"));
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
    }
}
