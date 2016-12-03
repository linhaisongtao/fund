package com.example.daisongsong.chartapp.book.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;

/**
 * Created by daisongsong on 2016/12/3.
 */

public class PriceItemView extends FrameLayout {
    private TextView mTextViewItemName;
    private TextView mTextViewItemValue;
    private TextView mTextViewItemValue2;

    public PriceItemView(Context context) {
        super(context);
        init();
    }

    public PriceItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PriceItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_price_item_view, this);
        mTextViewItemValue = (TextView) findViewById(R.id.mTextViewItemValue);
        mTextViewItemName = (TextView) findViewById(R.id.mTextViewItemName);
        mTextViewItemValue2 = (TextView) findViewById(R.id.mTextViewItemValue2);
    }

    public void setInfo(String name, String value) {
        setInfo(name, value, null, false);
    }

    public void setInfo(String name, String value, String value2, boolean profit) {
        mTextViewItemName.setText(name);
        mTextViewItemValue.setText(value);
        mTextViewItemValue.setTextColor(Color.BLACK);

        if (TextUtils.isEmpty(value2)) {
            mTextViewItemValue2.setVisibility(View.GONE);
        } else {
            mTextViewItemValue2.setVisibility(View.VISIBLE);
            mTextViewItemValue2.setText("(" + value2 + ")");
            if (profit) {
                mTextViewItemValue2.setTextColor(Color.RED);
                mTextViewItemValue.setTextColor(Color.RED);
            } else {
                mTextViewItemValue2.setTextColor(Color.GREEN);
                mTextViewItemValue.setTextColor(Color.GREEN);
            }
        }
    }

}
