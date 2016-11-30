package com.example.daisongsong.chartapp.book.addfund;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daisongsong.chartapp.R;
import com.example.daisongsong.chartapp.book.data.FundManager;
import com.example.daisongsong.chartapp.book.model.FundInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daisongsong on 2016/11/30.
 */

public class AddFundActivity extends Activity {
    private EditText mEditTextName;
    private EditText mEditTextCode;

    public static void startForResult(Activity activity, int requestCode) {
        Intent i = new Intent(activity, AddFundActivity.class);
        activity.startActivityForResult(i, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fund);

        mEditTextName = (EditText) findViewById(R.id.mEditTextName);
        mEditTextCode = (EditText) findViewById(R.id.mEditTextCode);

        findViewById(R.id.mButtonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditTextName.getText().toString();
                String code = mEditTextCode.getText().toString();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(code)){
                    Toast.makeText(v.getContext(), "输入基金代码以及名称", Toast.LENGTH_SHORT).show();
                }else {
                    List<FundInfo> allFunds = FundManager.getAllFunds();
                    for (FundInfo fundInfo : allFunds) {
                        if(fundInfo.getFundCode().equalsIgnoreCase(code)){
                            Toast.makeText(v.getContext(), "已经存在", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    FundInfo info = new FundInfo();
                    info.setFundCode(code);
                    info.setName(name);
                    allFunds.add(0, info);
                    FundManager.writeAllFunds(allFunds);

                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }
}
