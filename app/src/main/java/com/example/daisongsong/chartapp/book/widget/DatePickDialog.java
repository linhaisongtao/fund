package com.example.daisongsong.chartapp.book.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.example.daisongsong.chartapp.R;

/**
 * Created by daisongsong on 2016/12/1.
 */

public class DatePickDialog extends Dialog {
    private DatePicker mDatePicker;

    private OnDateSelectedListener mListener;

    public DatePickDialog(Context context, OnDateSelectedListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_date_pick);

        mDatePicker = (DatePicker) findViewById(R.id.mDatePicker);

        findViewById(R.id.mButtonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    String date = String.format("%d-%d-%d", mDatePicker.getYear(), mDatePicker.getMonth() + 1, mDatePicker.getDayOfMonth());
                    mListener.onDateSelected(DatePickDialog.this, date);
                }
            }
        });
    }

    public interface OnDateSelectedListener {
        void onDateSelected(DatePickDialog dialog, String date);
    }

}
