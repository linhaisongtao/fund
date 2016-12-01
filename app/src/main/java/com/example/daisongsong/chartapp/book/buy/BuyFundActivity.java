package com.example.daisongsong.chartapp.book.buy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.model.FundInfo;

/**
 * Created by daisongsong on 2016/12/1.
 */

public class BuyFundActivity extends Activity {
    private FundInfo.FundPrice mFundPrice;

    private TextView mTextViewName;
    private TextView mTextViewCode;
    private TextView mTextViewDate;
    private TextView mTextViewPrice;

    private EditText mEditTextFee;
    private EditText mEditTextMoney;
    private TextView mTextViewCount;
    private TextWatcher mAutoComputeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String moneyString = mEditTextMoney.getText().toString();
            String feeString = mEditTextFee.getText().toString();
            if (TextUtils.isEmpty(moneyString)) {
                return;
            }

            float money = Float.parseFloat(moneyString);
            float fee = TextUtils.isEmpty(feeString) ? 0f : Float.parseFloat(feeString);

            float count = money * (1 - fee / 100) / mFundPrice.getPrice();
            mTextViewCount.setText(String.valueOf(count));
        }
    };

    public static void start(Context context, FundInfo.FundPrice price) {
        Intent i = new Intent(context, BuyFundActivity.class);
        i.putExtra("price", price);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_fund);

        mFundPrice = (FundInfo.FundPrice) getIntent().getSerializableExtra("price");

        mTextViewCode = (TextView) findViewById(R.id.mTextViewCode);
        mTextViewPrice = (TextView) findViewById(R.id.mTextViewPrice);
        mTextViewDate = (TextView) findViewById(R.id.mTextViewDate);
        mTextViewName = (TextView) findViewById(R.id.mTextViewName);
        mEditTextFee = (EditText) findViewById(R.id.mEditTextFee);
        mEditTextMoney = (EditText) findViewById(R.id.mEditTextMoney);
        mTextViewCount = (TextView) findViewById(R.id.mTextViewCount);

        mTextViewCode.setText(mFundPrice.getFundInfo().getFundCode());
        mTextViewName.setText(mFundPrice.getFundInfo().getName());
        mTextViewPrice.setText(String.valueOf(mFundPrice.getPrice()));
        mTextViewDate.setText(mFundPrice.getDate());

        mEditTextMoney.addTextChangedListener(mAutoComputeWatcher);
        mEditTextFee.addTextChangedListener(mAutoComputeWatcher);
    }
}
