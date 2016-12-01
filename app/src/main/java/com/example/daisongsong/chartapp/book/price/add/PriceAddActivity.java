package com.example.daisongsong.chartapp.book.price.add;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.data.FundManager;
import com.example.daisongsong.chartapp.book.model.FundInfo;
import com.example.daisongsong.chartapp.book.widget.DatePickDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by daisongsong on 2016/12/1.
 */

public class PriceAddActivity extends Activity {
    private EditText mEditTextPrice;
    private TextView mTextViewCode;
    private TextView mTextViewName;

    private TextView mTextViewDate;

    private FundInfo mFundInfo;

    public static void start(Context context, FundInfo fundInfo) {
        Intent intent = new Intent(context, PriceAddActivity.class);
        intent.putExtra("fundInfo", fundInfo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_add);
        mEditTextPrice = (EditText) findViewById(R.id.mEditTextPrice);
        mTextViewCode = (TextView) findViewById(R.id.mTextViewCode);
        mTextViewName = (TextView) findViewById(R.id.mTextViewName);
        mTextViewDate = (TextView) findViewById(R.id.mTextViewDate);

        mFundInfo = (FundInfo) getIntent().getSerializableExtra("fundInfo");
        mTextViewCode.setText(mFundInfo.getFundCode());
        mTextViewName.setText(mFundInfo.getName());

        mTextViewDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                long time = 0;
                try {
                    time = new SimpleDateFormat("yyyy-M-d").parse(mTextViewDate.getText().toString()).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                FundInfo.FundPrice price = FundManager.findFundPrice(mFundInfo.getFundCode(), time);
                if (price != null) {
                    mEditTextPrice.setText(String.valueOf(price.getPrice()));
                } else {
                    mEditTextPrice.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTextViewDate.setText(new SimpleDateFormat("yyyy-M-d").format(new Date()));

        mTextViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickDialog dialog = new DatePickDialog(PriceAddActivity.this, new DatePickDialog.OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(DatePickDialog d, String date) {
                        mTextViewDate.setText(date);
                        d.dismiss();
                    }
                });
                dialog.show();
            }
        });

        findViewById(R.id.mButtonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float price = Float.parseFloat(mEditTextPrice.getText().toString());
                String date = mTextViewDate.getText().toString();
                long time = 0;
                try {
                    time = new SimpleDateFormat("yyyy-M-d").parse(date).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(PriceAddActivity.this, "日期格式化错误！", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<FundInfo.FundPrice> allFundPrice = FundManager.getAllFundPrice(mFundInfo.getFundCode());

                int indexToInsert = allFundPrice.size();
                for (int i = indexToInsert - 1; i >= 0; --i) {
                    if (allFundPrice.get(i).getTime() < time) {
                        indexToInsert = i + 1;
                        break;
                    }
                }

                if (indexToInsert < allFundPrice.size() && allFundPrice.get(indexToInsert).getTime() == time) {
                    allFundPrice.get(indexToInsert).setPrice(price);
                } else {
                    FundInfo.FundPrice p = new FundInfo.FundPrice();
                    p.setDate(date);
                    p.setTime(time);
                    p.setPrice(price);
                    allFundPrice.add(indexToInsert, p);
                }

                FundManager.writeAllFundPrice(mFundInfo.getFundCode(), allFundPrice);
                finish();
            }
        });
    }
}
