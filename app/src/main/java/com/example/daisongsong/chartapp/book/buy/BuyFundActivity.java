package com.example.daisongsong.chartapp.book.buy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.data.FundManager;
import com.example.daisongsong.chartapp.book.model.BuyInfo;
import com.example.daisongsong.chartapp.book.model.FundPrice;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by daisongsong on 2016/12/1.
 */

public class BuyFundActivity extends Activity {
    private FundPrice mFundPrice;

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

            float money = TextUtils.isEmpty(moneyString) ? 0f : Float.parseFloat(moneyString);
            float fee = TextUtils.isEmpty(feeString) ? 0f : Float.parseFloat(feeString);

            float count = money * (1 - fee / 100) / mFundPrice.getPrice();
            mTextViewCount.setText(String.valueOf(count));
        }
    };

    public static void start(Context context, FundPrice price) {
        Intent i = new Intent(context, BuyFundActivity.class);
        i.putExtra("price", price);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_fund);

        mFundPrice = (FundPrice) getIntent().getSerializableExtra("price");

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

        findViewById(R.id.mButtonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moneyString = mEditTextMoney.getText().toString();
                String feeString = mEditTextFee.getText().toString();
                float money = TextUtils.isEmpty(moneyString) ? 0f : Float.parseFloat(moneyString);
                float fee = TextUtils.isEmpty(feeString) ? 0f : Float.parseFloat(feeString);
                if (money > 0) {
                    buy(money, fee);
                    finish();
                }
            }
        });
    }

    private void buy(float money, final float fee) {
        List<BuyInfo> buyInfos = FundManager.getBuyInfos(mFundPrice.getFundInfo().getFundCode());
        BuyInfo info = new BuyInfo();
        info.setFundPrice(mFundPrice);
        info.setFundTime(mFundPrice.getTime());
        info.setMoney(money);
        info.setFee(fee);
        buyInfos.add(info);

        Collections.sort(buyInfos, new Comparator<BuyInfo>() {
            @Override
            public int compare(BuyInfo lhs, BuyInfo rhs) {
                long diff = (lhs.getFundPrice().getTime() - rhs.getFundPrice().getTime());
                if (diff > 0) {
                    return 1;
                } else if (diff == 0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        FundManager.saveBuyInfos(mFundPrice.getFundInfo().getFundCode(), buyInfos);
    }
}
